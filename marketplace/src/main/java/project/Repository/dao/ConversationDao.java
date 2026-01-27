package project.Repository.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import project.Repository.Entities.ConversationEntity;

public interface ConversationDao extends JpaRepository<ConversationEntity, ConversationEntity.ConversationId>
{
    List<ConversationEntity> getBySender(Integer sender);
}
