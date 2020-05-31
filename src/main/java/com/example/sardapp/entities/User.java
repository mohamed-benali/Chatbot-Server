package com.example.sardapp.entities;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "users")
public class User
{
    @Id @Column(name = "email")             private String email;
    @Column(name = "password")              private String password;

    @Column(name = "public")                private Boolean publicProfile;
    @Column(name = "profileImage")          private byte[] image;
    @Column(name = "imageType")             private String imageType;
    @Column(name = "name")                  private String name;
    @Column(name = "description")           private String description;
    @Column(name = "phoneNumber")           private Integer phoneNumber;
    @JsonFormat(pattern="yyyy-mm-dd")
    @Column(name = "birthday")              private Date birthday;
    @Column(name = "vehicle")               private Boolean vehicle;
    @Column(name = "comarca")               private String comarca;

    @Column(name = "aplecs")                private Boolean aplecs;
    @Column(name = "ballades")              private Boolean ballades;
    @Column(name = "concerts")              private Boolean concerts;
    @Column(name = "concursos")             private Boolean concursos;
    @Column(name = "cursets")               private Boolean cursets;
    @Column(name = "altres")                private Boolean altres;

    @Column(name = "comptarRepartir")       private Boolean comptarRepartir;
    @Column(name = "competidor")            private Boolean competidor;
    @Column(name = "coblaCompeticio")       private Boolean coblaCompeticio;

    public User() {}

    public String getEmail()
    {
        return email;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }

    public String getPassword()
    {
        return password;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }

    public Boolean getPublicProfile()
    {
        return publicProfile;
    }

    public void setPublicProfile(Boolean publicProfile)
    {
        this.publicProfile = publicProfile;
    }

    public byte[] getImage()
    {
        return image;
    }

    public void setImage(byte[] image)
    {
        this.image = image;
    }

    public String getImageType()
    {
        return imageType;
    }

    public void setImageType(String imageType)
    {
        this.imageType = imageType;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public Integer getPhoneNumber()
    {
        return phoneNumber;
    }

    public void setPhoneNumber(Integer phoneNumber)
    {
        this.phoneNumber = phoneNumber;
    }

    public Date getBirthday()
    {
        return birthday;
    }

    public void setBirthday(Date birthday)
    {
        this.birthday = birthday;
    }

    public Boolean getVehicle()
    {
        return vehicle;
    }

    public void setVehicle(Boolean vehicle)
    {
        this.vehicle = vehicle;
    }

    public String getComarca()
    {
        return comarca;
    }

    public void setComarca(String comarca)
    {
        this.comarca = comarca;
    }

    public Boolean getAplecs()
    {
        return aplecs;
    }

    public void setAplecs(Boolean aplecs)
    {
        this.aplecs = aplecs;
    }

    public Boolean getBallades()
    {
        return ballades;
    }

    public void setBallades(Boolean ballades)
    {
        this.ballades = ballades;
    }

    public Boolean getConcerts()
    {
        return concerts;
    }

    public void setConcerts(Boolean concerts)
    {
        this.concerts = concerts;
    }

    public Boolean getConcursos()
    {
        return concursos;
    }

    public void setConcursos(Boolean concursos)
    {
        this.concursos = concursos;
    }

    public Boolean getCursets()
    {
        return cursets;
    }

    public void setCursets(Boolean cursets)
    {
        this.cursets = cursets;
    }

    public Boolean getAltres()
    {
        return altres;
    }

    public void setAltres(Boolean altres)
    {
        this.altres = altres;
    }

    public Boolean getComptarRepartir()
    {
        return comptarRepartir;
    }

    public void setComptarRepartir(Boolean comptarRepartir)
    {
        this.comptarRepartir = comptarRepartir;
    }

    public Boolean getCompetidor()
    {
        return competidor;
    }

    public void setCompetidor(Boolean competidor)
    {
        this.competidor = competidor;
    }

    public Boolean getCoblaCompeticio()
    {
        return coblaCompeticio;
    }

    public void setCoblaCompeticio(Boolean coblaCompeticio)
    {
        this.coblaCompeticio = coblaCompeticio;
    }
}
