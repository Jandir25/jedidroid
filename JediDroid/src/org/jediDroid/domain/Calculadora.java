package org.jediDroid.domain;

public class Calculadora {

	Double resultat;
	String operador;
	
	public Calculadora() {
		this.resultat = null;
		this.operador = null;
	}

	
	public Double getResultat() {
		return resultat;
	}

	public void setResultat(Double resultat) {
		if (this.operador == null) {
			this.resultat = resultat;
		}
		else {
			if (this.operador == "+") {
				this.resultat += resultat;
			}
			else if (this.operador == "-") {
				this.resultat -= resultat;
			}
			else if (this.operador == "*") {
				this.resultat *= resultat;
			}
			else if (this.operador == "/") {
				if (resultat != 0) {
					this.resultat /= resultat;
				}
			}
			
		}
	}

	public String getOperador() {
		return operador;
	}

	public void setOperador(String operador) {
		this.operador = operador;
	}
	
	public void suma(Double valor) {
		resultat += valor;
	}
	
	public void resta (Double valor) {
		resultat -= valor;
	}
	
	public void multiplicacio (Double valor) {
		resultat *= valor;
	}
	
	public void divisio(Double valor) {
		if (valor != 0) {
			resultat /= valor;
		}
	}
	
}
