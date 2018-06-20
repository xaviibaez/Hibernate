// default package
// Generated 12-feb-2018 17:51:12 by Hibernate Tools 4.0.1.Final

import java.util.HashSet;
import java.util.Set;

/**
 * Grupmoodle generated by hbm2java
 */
public class Grupmoodle implements java.io.Serializable {

	private int idGrupMoodle;
	private Usuari usuari;
	private String nomGrupMoodle;
	private String desciripcioGrupMoodle;
	private Boolean actiu;
	private boolean autoInscripcioGrup;
	private Set<Inscripcions> inscripcionses = new HashSet<Inscripcions>(0);

	public Grupmoodle() {
	}

	public Grupmoodle(int idGrupMoodle, boolean autoInscripcioGrup) {
		this.idGrupMoodle = idGrupMoodle;
		this.autoInscripcioGrup = autoInscripcioGrup;
	}

	public Grupmoodle(int idGrupMoodle, Usuari usuari, String nomGrupMoodle, String desciripcioGrupMoodle,
			Boolean actiu, boolean autoInscripcioGrup, Set<Inscripcions> inscripcionses) {
		this.idGrupMoodle = idGrupMoodle;
		this.usuari = usuari;
		this.nomGrupMoodle = nomGrupMoodle;
		this.desciripcioGrupMoodle = desciripcioGrupMoodle;
		this.actiu = actiu;
		this.autoInscripcioGrup = autoInscripcioGrup;
		this.inscripcionses = inscripcionses;
	}

	public int getIdGrupMoodle() {
		return this.idGrupMoodle;
	}

	public void setIdGrupMoodle(int idGrupMoodle) {
		this.idGrupMoodle = idGrupMoodle;
	}

	public Usuari getUsuari() {
		return this.usuari;
	}

	public void setUsuari(Usuari usuari) {
		this.usuari = usuari;
	}

	public String getNomGrupMoodle() {
		return this.nomGrupMoodle;
	}

	public void setNomGrupMoodle(String nomGrupMoodle) {
		this.nomGrupMoodle = nomGrupMoodle;
	}

	public String getDesciripcioGrupMoodle() {
		return this.desciripcioGrupMoodle;
	}

	public void setDesciripcioGrupMoodle(String desciripcioGrupMoodle) {
		this.desciripcioGrupMoodle = desciripcioGrupMoodle;
	}

	public Boolean getActiu() {
		return this.actiu;
	}

	public void setActiu(Boolean actiu) {
		this.actiu = actiu;
	}

	public boolean isAutoInscripcioGrup() {
		return this.autoInscripcioGrup;
	}

	public void setAutoInscripcioGrup(boolean autoInscripcioGrup) {
		this.autoInscripcioGrup = autoInscripcioGrup;
	}

	public Set<Inscripcions> getInscripcionses() {
		return this.inscripcionses;
	}

	public void setInscripcionses(Set<Inscripcions> inscripcionses) {
		this.inscripcionses = inscripcionses;
	}

}
