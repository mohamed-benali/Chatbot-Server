package com.example.sardapp.entities;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "assistents")
public class Assistent
{
    @EmbeddedId
    private AssistentId id;

    public Assistent() {}

    public Assistent(AssistentId id)
    {
        this.id = id;
    }

    public AssistentId getId()
    {
        return id;
    }

    public void setId(AssistentId id)
    {
        this.id = id;
    }
}
