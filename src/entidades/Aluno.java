package entidades;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Aluno {

	private static final Double MEDIA_APROVACAO = 7.0;
	private Integer matricula;
	private String nome;
	private Integer idade;
	private List<Disciplina> disciplinas;
	
	public Aluno(Integer matricula, String nome, Integer idade) {
		this(matricula, nome, idade, new ArrayList<>());
	}
	
	public Aluno(Integer matricula, String nome, Integer idade, List<Disciplina> disciplinas) {
		this.matricula = matricula;
		this.nome = nome;
		this.idade = idade;
		this.disciplinas = disciplinas;
	}
	
	public Integer getMatricula() {
		return matricula;
	}
	
	public void setMatricula(Integer matricula) {
		this.matricula = matricula;
	}
	
	public String getNome() {
		return nome;
	}
	
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public Integer getIdade() {
		return idade;
	}
	
	public void setIdade(Integer idade) {
		this.idade = idade;
	}
	
	public List<Disciplina> getDisciplinas() {
		return disciplinas;
	}
	
	public void setDisciplinas(List<Disciplina> disciplinas) {
		this.disciplinas = disciplinas;
	}
	
	public void adicionarDisciplina(Disciplina disciplina) {
		disciplinas.add(disciplina);
	}
	
	public List<Disciplina> buscarDisciplinasAprovadas() {
		return disciplinas.stream().collect(Collectors.partitioningBy(d -> d.buscarMedia() >= MEDIA_APROVACAO)).get(true);
	}
	
	public List<Disciplina> buscarDisciplinasReprovadas() {
		return disciplinas.stream().collect(Collectors.partitioningBy(d -> d.buscarMedia() >= MEDIA_APROVACAO)).get(false);
	}
	
	public List<Disciplina> buscarDisciplinasObrigatorias() {
		return disciplinas.stream().collect(Collectors.groupingBy(Disciplina::getOptativa)).get(false);
	}
	
	public List<Disciplina> buscarDisciplinasOptativas() {
		Map<Boolean, List<Disciplina>> resultado = disciplinas.stream().collect(Collectors.groupingBy(Disciplina::getOptativa));
		return resultado.get(true);
	}
	
	public Long buscarQuantidadeDisciplinasObrigatorias() {
		return disciplinas.stream().collect(Collectors.groupingBy(Disciplina::getOptativa, Collectors.counting())).get(false);
	}
	
	public Long buscarQuantidadeDisciplinasOptativas() {
		Map<Boolean, Long> resultado = disciplinas.stream().collect(Collectors.groupingBy(Disciplina::getOptativa, Collectors.counting()));
		return resultado.get(true);
	}
	
	public Boolean isAprovado() {
		return buscarDisciplinasReprovadas().isEmpty();
	}
}
