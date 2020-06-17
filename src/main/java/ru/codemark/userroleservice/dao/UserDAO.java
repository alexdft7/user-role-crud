package ru.codemark.userroleservice.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.codemark.userroleservice.entity.Role;
import ru.codemark.userroleservice.entity.User;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Repository
public class UserDAO {

    private SessionFactory sessionFactory;

    @Autowired
    public UserDAO(SessionFactory sessionFactory) {
        this.sessionFactory=sessionFactory;
    }

    @SuppressWarnings("unchecked")
    public User getUser(Long id) {
        Session currentSession = sessionFactory.getCurrentSession();
        Query<User> userQuery = currentSession.createQuery("select user from User user left join fetch user.roles where user.id = :id");
        return userQuery.setParameter("id", id).getSingleResult();
    }

    @SuppressWarnings("unchecked")
    public List<User> getUsers() {
        Session currentSession = sessionFactory.getCurrentSession();
        Query<User> usersQuery = currentSession.createQuery("select distinct user from User user left join fetch user.roles order by user.id");
        return usersQuery.getResultList();
    }

    public void saveUser(User user) {
        Session currentSession = sessionFactory.getCurrentSession();
        user.setRoles(getRolesForUser(user));
        currentSession.merge(user);
    }

    public void updateUser(User user) {
        Session currentSession = sessionFactory.getCurrentSession();
        getRolesForUser(user);
        currentSession.merge(user);
    }

    public void deleteUser(Long id) {
        Session currentSession = sessionFactory.getCurrentSession();
        Query deleteQuery = currentSession.createQuery("delete from User user where user.id=:id");
        deleteQuery.setParameter("id", id).executeUpdate();
    }

    private Set<Role> getRolesForUser(User user) {
        Session currentSession = sessionFactory.getCurrentSession();

        Set<Role> userRoles = user.getRoles();
        Set<Role> allRoles = currentSession.createQuery("SELECT roles FROM Role roles", Role.class)
                .getResultStream()
                .collect(Collectors.toSet());
        Set<Role> finalSet = new HashSet<>();

        for (Role role : allRoles) {
            for (Role userRole : userRoles) {
                if (userRole.getRole().equals(role.getRole())) {
                    userRole.setId(role.getId());
                    userRole.setRole(role.getRole());
                    finalSet.add(userRole);
                }
            }
        }
        return finalSet;
    }
}