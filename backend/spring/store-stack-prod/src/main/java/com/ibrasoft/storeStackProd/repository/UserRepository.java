package com.ibrasoft.storeStackProd.repository;

import java.util.Date;
import java.util.List;

import com.ibrasoft.storeStackProd.beans.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface UserRepository extends JpaRepository<User, Integer>, JpaSpecificationExecutor<User> {
        User findByUsername(String username);

        boolean existsByUsername(String username);

        @Query("UPDATE User u SET u.status = :status, u.lastUpdateOn = :lastUpdateOn WHERE u.id = :userId")
        User deleteUser(@Param("status") short userStatus, @Param("lastUpdateOn") Date lastUpdateOn,
                        @Param("userId") int userId);

        @Query("SELECT u FROM User u WHERE u.status = :activateStatus OR u.status = :deActivateStatus")
        List<User> getAllUser(@Param("activateStatus") short userStatus,
                        @Param("deActivateStatus") short userDeActivateStatus);

        @Query("SELECT u FROM User u WHERE u.status = :archiveStatus")
        List<User> getAllArchivedUser(@Param("archiveStatus") short archiveStatus);

        @Query("select p from User p where upper(p.firstName) like concat('%', upper(?1), '%') or upper(p.lastName) like concat('%', upper(?1), '%') ")
        List<User> getByNameOrSurname(String term);

        @Query("select p from User p where upper(p.email) like concat('%', upper(?1), '%')")
        List<User> findByUserContainingIgnoreCase(String term);

        @Query("select p from User p WHERE p.email = :email")
        User findByEmail(@Param("email") String email);

        @Transactional
        @Modifying
        @Query("UPDATE User u SET u.username = :username, u.firstName = :firstName, u.lastUpdateOn = :lastUpdateOn, u.lastName = :lastName, u.email = :email, u.comment = :comment, u.phone = :phone, u.birthDate = :birthDate  WHERE u.id = :id")
        void updateAdminOrSpecialistByAdmin(@Param("username") String username, @Param("firstName") String firstName,
                        @Param("lastUpdateOn") Date lastUpdateOn, @Param("lastName") String lastName,
                        @Param("email") String email, @Param("comment") String comment,
                        @Param("phone") String phone, @Param("birthDate") Date birthDate,
                        @Param("id") Integer id);

        @Transactional
        @Modifying
        @Query("UPDATE User u SET u.userImagePath = :userImagePath  WHERE u.id = :id")
        void updateImage(@Param("id") Integer id, @Param("userImagePath") String userImagePath);

        @Transactional
        @Modifying
        @Query("UPDATE User u SET u.status = :status  WHERE u.id = :id")
        void updateUserStatus(@Param("id") Integer id, @Param("status") short status);

}
