package com.example.sardapp.entities;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "actes")
public class Acte {

    @Id @Column(name = "id")                    private Integer id;                     //Opcional a mostrar
    @Column(name = "tipus")                     private String tipus;                   //S'ha de mostrar
    @Column(name = "dia")                       private Date dia;                     //S'ha de mostrar
    @Column(name = "hora1")                     private String hora1;                   //S'ha de mostrar
    @Column(name = "hora2")                     private String hora2;                   //S'ha de mostrar
    @Column(name = "hora3")                     private String hora3;                   //S'ha de mostrar
    //@Column(name = "destacat")                  private String destacat;
    @Column(name = "anul")                      private String anul;                    //Mostrar i Fer-ho molt visual
    @Column(name = "mode")                      private String mode;                    //NO s'ha de mostrar
    @Column(name = "nomActivitat")              private String nomActivitat;            //S'ha de mostrar
    //@Column(name = "nomiEstatActivitat")        private String nomiEstatActivitat;
    @Column(name = "mesDades")                  private String mesDades;                //S'ha de mostrar
    @Column(name = "linkProgHTML")              private String linkProgHTML;            //Opcional a mostrar
    @Column(name = "linkProgPDF")               private String linkProgPDF;             //Opcional a mostrar
    //@Column(name = "mesDadesLink")              private String mesDadesLink;
    @Column(name = "lloc")                      private String lloc;                    //S'ha de mostrar
    @Column(name = "latitud")                   private float latitud;                  //Opcional a mostrar
    @Column(name = "longitud")                  private float longitud;                 //Opcional a mostrar
    @Column(name = "latitudAprox")              private float latitudAprox;             //Opcional a mostrar
    @Column(name = "longitudAprox")             private float longitudAprox;            //Opcional a mostrar
    @Column(name = "llocSiPlou")                private String llocSiPlou;              //Es recomana mostrar
    @Column(name = "latitudSiPlou")             private float latitudSiPlou;            //Opcional a mostrar
    @Column(name = "longitudSiPlou")            private float longitudSiPlou;           //Opcional a mostrar
    @Column(name = "latitudSiPlouAprox")        private float latitudSiPlouAprox;       //Opcional a mostrar
    @Column(name = "longitudSiPlouAprox")       private float longitudSiPlouAprox;      //Opcional a mostrar

    //@Column(name = "municipi")                  private String municipi;                //S'ha de mostrar
    //@Column(name = "poblacioCurta")             private String poblacioCurta;           //S'ha de mostrar
    @Column(name = "poblacioMitjana")           private String poblacioMitjana;         //S'ha de mostrar
    //@Column(name = "poblacioAmpliada")          private String poblacioAmpliada;        //S'ha de mostrar

    @Column(name = "comarca")                   private String comarca;                 //Opcional a mostrar
    @Column(name = "territori")                 private String territori;               //Opcional a mostrar
    @Column(name = "cobla1")                    private String cobla1;                  //S'ha de mostrar
    @Column(name = "cobla2")                    private String cobla2;                  //S'ha de mostrar
    @Column(name = "cobla3")                    private String cobla3;                  //S'ha de mostrar
    @Column(name = "cobla4")                    private String cobla4;                  //S'ha de mostrar
    @Column(name = "cobla5")                    private String cobla5;                  //S'ha de mostrar
    @Column(name = "cobla6")                    private String cobla6;                  //S'ha de mostrar
    @Column(name = "cobla7")                    private String cobla7;                  //S'ha de mostrar
    @Column(name = "musicsNoCobla")             private String musicsNoCobla;           //S'ha de mostrar / no utilitzar per filtrar
    @Column(name = "musicsNoCobla2")            private String musicsNoCobla2;          //S'ha de mostrar / no utilitzar per filtrar
    //@Column(name = "totsElsInterprets")         private String totsElsInterprets;
    @Column(name = "imatge")                    private String imatge;                  //S'ha de mostrar

    public Acte() {}

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTipus() {
        return tipus;
    }

    public void setTipus(String tipus) {
        this.tipus = tipus;
    }

    public Date getDia() {
        return dia;
    }

    public void setDia(Date dia) {
        this.dia = dia;
    }

    public String getHora1() {
        return hora1;
    }

    public void setHora1(String hora1) {
        this.hora1 = hora1;
    }

    public String getHora2() {
        return hora2;
    }

    public void setHora2(String hora2) {
        this.hora2 = hora2;
    }

    public String getHora3() {
        return hora3;
    }

    public void setHora3(String hora3) {
        this.hora3 = hora3;
    }

    public String getAnul() {
        return anul;
    }

    public void setAnul(String anul) {
        this.anul = anul;
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public String getNomActivitat() {
        return nomActivitat;
    }

    public void setNomActivitat(String nomActivitat) {
        this.nomActivitat = nomActivitat;
    }

    public String getMesDades() {
        return mesDades;
    }

    public void setMesDades(String mesDades) {
        this.mesDades = mesDades;
    }

    public String getLinkProgHTML() {
        return linkProgHTML;
    }

    public void setLinkProgHTML(String linkProgHTML) {
        this.linkProgHTML = linkProgHTML;
    }

    public String getLinkProgPDF() {
        return linkProgPDF;
    }

    public void setLinkProgPDF(String linkProgPDF) {
        this.linkProgPDF = linkProgPDF;
    }

    public String getLloc() {
        return lloc;
    }

    public void setLloc(String lloc) {
        this.lloc = lloc;
    }

    public float getLatitud() {
        return latitud;
    }

    public void setLatitud(float latitud) {
        this.latitud = latitud;
    }

    public float getLongitud() {
        return longitud;
    }

    public void setLongitud(float longitud) {
        this.longitud = longitud;
    }

    public float getLatitudAprox() {
        return latitudAprox;
    }

    public void setLatitudAprox(float latitudAprox) {
        this.latitudAprox = latitudAprox;
    }

    public float getLongitudAprox() {
        return longitudAprox;
    }

    public void setLongitudAprox(float longitudAprox) {
        this.longitudAprox = longitudAprox;
    }

    public String getLlocSiPlou() {
        return llocSiPlou;
    }

    public void setLlocSiPlou(String llocSiPlou) {
        this.llocSiPlou = llocSiPlou;
    }

    public float getLatitudSiPlou() {
        return latitudSiPlou;
    }

    public void setLatitudSiPlou(float latitudSiPlou) {
        this.latitudSiPlou = latitudSiPlou;
    }

    public float getLongitudSiPlou() {
        return longitudSiPlou;
    }

    public void setLongitudSiPlou(float longitudSiPlou) {
        this.longitudSiPlou = longitudSiPlou;
    }

    public float getLatitudSiPlouAprox() {
        return latitudSiPlouAprox;
    }

    public void setLatitudSiPlouAprox(float latitudSiPlouAprox) {
        this.latitudSiPlouAprox = latitudSiPlouAprox;
    }

    public float getLongitudSiPlouAprox() {
        return longitudSiPlouAprox;
    }

    public void setLongitudSiPlouAprox(float longitudSiPlouAprox) {
        this.longitudSiPlouAprox = longitudSiPlouAprox;
    }

   /* public String getMunicipi() {
        return municipi;
    } */

    /* public void setMunicipi(String municipi) {
        this.municipi = municipi;
    } */

    /* public String getPoblacioCurta() {
        return poblacioCurta;
    } */

    /* public void setPoblacioCurta(String poblacioCurta) {
        this.poblacioCurta = poblacioCurta;
    } */

    public String getPoblacioMitjana() {
        return poblacioMitjana;
    }

    public void setPoblacioMitjana(String poblacioMitjana) {
        this.poblacioMitjana = poblacioMitjana;
    }

    /* public String getPoblacioAmpliada() {
        return poblacioAmpliada;
    } */

    /* public void setPoblacioAmpliada(String poblacioAmpliada) {
        this.poblacioAmpliada = poblacioAmpliada;
    } */

    public String getComarca() {
        return comarca;
    }

    public void setComarca(String comarca) {
        this.comarca = comarca;
    }

    public String getTerritori() {
        return territori;
    }

    public void setTerritori(String territori) {
        this.territori = territori;
    }

    public String getCobla1() {
        return cobla1;
    }

    public void setCobla1(String cobla1) {
        this.cobla1 = cobla1;
    }

    public String getCobla2() {
        return cobla2;
    }

    public void setCobla2(String cobla2) {
        this.cobla2 = cobla2;
    }

    public String getCobla3() {
        return cobla3;
    }

    public void setCobla3(String cobla3) {
        this.cobla3 = cobla3;
    }

    public String getCobla4() {
        return cobla4;
    }

    public void setCobla4(String cobla4) {
        this.cobla4 = cobla4;
    }

    public String getCobla5() {
        return cobla5;
    }

    public void setCobla5(String cobla5) {
        this.cobla5 = cobla5;
    }

    public String getCobla6() {
        return cobla6;
    }

    public void setCobla6(String cobla6) {
        this.cobla6 = cobla6;
    }

    public String getCobla7() {
        return cobla7;
    }

    public void setCobla7(String cobla7) {
        this.cobla7 = cobla7;
    }

    public String getMusicsNoCobla() {
        return musicsNoCobla;
    }

    public void setMusicsNoCobla(String musicsNoCobla) {
        this.musicsNoCobla = musicsNoCobla;
    }

    public String getMusicsNoCobla2() {
        return musicsNoCobla2;
    }

    public void setMusicsNoCobla2(String musicsNoCobla2) {
        this.musicsNoCobla2 = musicsNoCobla2;
    }

    public String getImatge() {
        return imatge;
    }

    public void setImatge(String imatge) {
        this.imatge = imatge;
    }
}
