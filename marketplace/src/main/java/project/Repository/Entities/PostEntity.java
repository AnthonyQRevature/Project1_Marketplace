package project.Repository.Entities;

import java.sql.Timestamp;

import org.hibernate.annotations.JdbcType;
import org.hibernate.dialect.type.PostgreSQLEnumJdbcType;

import jakarta.annotation.Nonnull;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name="post")
public class PostEntity {
    public static enum PostStatusEnum
    {
        AVAILABLE,
        UNLISTED,
        SOLID
    }

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    public Integer id;
    @ManyToOne(targetEntity=UserEntity.class)
    public Integer seller_id;
    @Nonnull
    public String description;
    @Nonnull
    public double price;
    @Enumerated(EnumType.STRING)
    @JdbcType(PostgreSQLEnumJdbcType.class)
    public PostStatusEnum status;
    public Timestamp createdAt;
    public Timestamp lastEditTime;
}
