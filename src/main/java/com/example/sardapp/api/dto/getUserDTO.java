package com.example.sardapp.api.dto;

import com.example.sardapp.entities.User;
import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.Column;
import javax.persistence.Id;
import java.util.Date;

public class getUserDTO
{
    private String email;
    private Boolean publicProfile;
    private byte[] image;
    private String imageType;
    private String name;
    private String description;
    private Integer phoneNumber;
    private Date birthday;
    private Boolean vehicle;
    private String comarca;

    private Boolean aplecs;
    private Boolean ballades;
    private Boolean concerts;
    private Boolean concursos;
    private Boolean cursets;
    private Boolean altres;

    private Boolean comptarRepartir;
    private Boolean competidor;
    private Boolean coblaCompeticio;

    public getUserDTO() {}

    public getUserDTO(User user)
    {
        this.email = user.getEmail();
        this.publicProfile = user.getPublicProfile();
        this.image = user.getImage();
        this.imageType = user.getImageType();
        this.name = user.getName();
        this.description = user.getDescription();
        this.phoneNumber = user.getPhoneNumber();
        this.birthday = user.getBirthday();
        this.vehicle = user.getVehicle();
        this.comarca = user.getComarca();
        this.aplecs = user.getAplecs();
        this.ballades = user.getBallades();
        this.concerts = user.getConcerts();
        this.concursos = user.getConcursos();
        this.cursets = user.getCursets();
        this.altres = user.getAltres();
        this.comptarRepartir = user.getComptarRepartir();
        this.competidor = user.getCompetidor();
        this.coblaCompeticio = user.getCoblaCompeticio();
    }

    public String getEmail()
    {
        return email;
    }

    public void setEmail(String email)
    {
        this.email = email;
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
