// default package
// Generated 12-feb-2018 17:51:12 by Hibernate Tools 4.0.1.Final

import java.util.HashSet;
import java.util.Set;

/**
 * Tipususuari generated by hbm2java
 */
public class Tipususuari implements java.io.Serializable {

	private Integer idTipusUsuari;
	private String descripcioTipusUsuari;
	private Set<Usuari> usuaris = new HashSet<Usuari>(0);

	public Tipususuari() {
	}

	public Tipususuari(String descripcioTipusUsuari) {
		this.descripcioTipusUsuari = descripcioTipusUsuari;
	}

	public Tipususuari(String descripcioTipusUsuari, Set<Usuari> usuaris) {
		this.descripcioTipusUsuari = descripcioTipusUsuari;
		this.usuaris = usuaris;
	}

	public Integer getIdTipusUsuari() {
		return this.idTipusUsuari;
	}

	public void setIdTipusUsuari(Integer idTipusUsuari) {
		this.idTipusUsuari = idTipusUsuari;
	}

	public String getDescripcioTipusUsuari() {
		return this.descripcioTipusUsuari;
	}

	public void setDescripcioTipusUsuari(String descripcioTipusUsuari) {
		this.descripcioTipusUsuari = descripcioTipusUsuari;
	}

	public Set<Usuari> getUsuaris() {
		return this.usuaris;
	}

	public void setUsuaris(Set<Usuari> usuaris) {
		this.usuaris = usuaris;
	}

}
