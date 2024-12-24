package com.sangto.whatsapp.repository;

import com.sangto.whatsapp.modal.Chat;
import com.sangto.whatsapp.modal.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ChatRepository extends JpaRepository<Chat, Integer> {

    @Query("SELECT c from Chat c JOIN c.users u WHERE u.id=:userId")
    public List<Chat> findChatByUserId(@Param("userId") Integer userId);

    @Query("SELECT c FROM Chat c WHERE c.is_group=false AND :user MEMBER OF c.users AND :userRequest MEMBER OF c.users")
    public Chat findSingleChatByUserIds(@Param("user")User user, @Param("userRequest")User userRequest);

}
