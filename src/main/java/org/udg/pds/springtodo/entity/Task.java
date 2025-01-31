package org.udg.pds.springtodo.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import jakarta.persistence.*;
import org.udg.pds.springtodo.serializer.JsonDateDeserializer;
import org.udg.pds.springtodo.serializer.JsonDateSerializer;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

@Entity
// This tells JAXB that it has to ignore getters and setters and only use fields for JSON marshaling/unmarshaling
public class Task implements Serializable {
    /**
     * Default value included to remove warning. Remove or modify at will.
     **/
    private static final long serialVersionUID = 1L;

    // This tells JAXB that this field can be used as ID
    // Since XmlID can only be used on Strings, we need to use LongAdapter to transform Long <-> String
    @Id
    // Don't forget to use the extra argument "strategy = GenerationType.IDENTITY" to get AUTO_INCREMENT
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonDeserialize(using = JsonDateDeserializer.class)
    @JsonSerialize(using = JsonDateSerializer.class)
    private ZonedDateTime dateCreated;

    @JsonDeserialize(using = JsonDateDeserializer.class)
    @JsonSerialize(using = JsonDateSerializer.class)
    private ZonedDateTime dateLimit;

    private Boolean completed;

    private String text;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "fk_user")
    private User user;

    @Column(name = "fk_user", insertable = false, updatable = false)
    private Long userId;

    @ManyToMany(cascade = CascadeType.ALL)
    private final Collection<Tag> tags = new ArrayList<>();

    public Task() {
    }

    public Task(ZonedDateTime dateCreated, ZonedDateTime dateLimit, Boolean completed, String text) {
        this.dateCreated = dateCreated;
        this.dateLimit = dateLimit;
        this.completed = completed;
        this.text = text;
    }

    @JsonView(Views.Private.class)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @JsonIgnore
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void addTag(Tag tag) {
        tags.add(tag);
    }

    @JsonView(Views.Complete.class)
    public Collection<Tag> getTags() {
        tags.size();
        return tags;
    }

    @JsonView(Views.Private.class)
    public Boolean getCompleted() {
        return completed;
    }

    @JsonView(Views.Private.class)
    public String getText() {
        return text;
    }

    @JsonView(Views.Complete.class)
    public long getUserId() {
        return userId;
    }

    @JsonView(Views.Private.class)
    public ZonedDateTime getDateCreated() {
        return dateCreated;
    }

    @JsonView(Views.Private.class)
    public ZonedDateTime getDateLimit() {
        return dateLimit;
    }
}
