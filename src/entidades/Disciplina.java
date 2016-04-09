package entidades;

import java.util.ArrayList;
import java.util.List;

public class Disciplina {

	private String nome;
	private Boolean optativa;
	private List<Double> notas;
	
	public Disciplina() {
		this("", false, new ArrayList<>());
	}
	
	public Disciplina(String nome) {
		this(nome, false, new ArrayList<>());
	}
	
	public Disciplina(String nome, Boolean optativa) {
		this(nome, optativa, new ArrayList<>());
	}
	
	public Disciplina(String nome, Boolean optativa, List<Double> notas) {
		this.nome = nome;
		this.optativa = optativa;
		this.notas = notas;
	}
	
	public String getNome() {
		return nome;
	}
	
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public Boolean getOptativa() {
		return optativa;
	}
	
	public void setOptativa(Boolean optativa) {
		this.optativa = optativa;
	}
	
	public List<Double> getNotas() {
		return notas;
	}
	
	public void setNotas(List<Double> notas) {
		this.notas = notas;
	}
	
	public void adicionarNota(Double nota) {
		notas.add(nota);
	}
	
	public Double buscarMedia() {
		return notas.stream().mapToDouble(n -> n).average().getAsDouble();
	}
}
