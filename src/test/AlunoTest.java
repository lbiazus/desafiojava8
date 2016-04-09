package test;

import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.DoubleSummaryStatistics;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import entidades.Aluno;
import entidades.Disciplina;

public class AlunoTest {

	private Aluno aluno;
	
	@Before
	public void setUp() {
		List<Disciplina> disciplinas = asList(
			new Disciplina("Desenvolvimento Web", false, asList(5.5, 7.5, 8.0, 9.0)), 
			new Disciplina("Algoritmos", false, asList(6.0, 7.5, 6.0, 9.0)),
			new Disciplina("Metodologia Científica", false, asList(5.5, 7.5, 7.0, 7.0)),
			new Disciplina("Libras", true, asList(6.5, 7.5, 8.0, 7.0)));
	
		aluno = new Aluno(1, "João", 21, disciplinas);
		
	}
	
	@Test
	public void testAlunoReprovado() { 
		assertThat(aluno.isAprovado()).isFalse();	
	}
	
	@Test
	public void testBuscarDisciplinasOptativas() { 
		assertThat(aluno.buscarDisciplinasOptativas()).hasSize(1);
	}
	
	@Test
	public void testBuscarQauntidadesDisciplinas() { 
		assertThat(aluno.buscarQuantidadeDisciplinasObrigatorias()).isEqualTo(3L);
		assertThat(aluno.buscarQuantidadeDisciplinasOptativas()).isEqualTo(1L);
	}
	
	@Test
	public void testBuscarDisciplinaComMaiorMedia() {
		Disciplina disciplina = aluno.getDisciplinas().stream().reduce((d1, d2) -> d1.buscarMedia() > d2.buscarMedia() ? d1 : d2).get();
		//Disciplina disciplina = aluno.getDisciplinas().stream().filter(d -> d.getOptativa()).reduce((d1, d2) -> d1.buscarMedia() > d2.buscarMedia() ? d1 : d2).get();
		
		assertThat(disciplina.getNome()).contains("Desenvolvimento Web");
	}
	
	@Test
	public void testCriarNovaDisciplina() {
		Disciplina cloneUltimaDisciplina = aluno.getDisciplinas().stream().reduce(new Disciplina(""), (d1, d2) -> {d1.setNome(d2.getNome()); d1.setOptativa(d2.getOptativa()); return d1;});
		
		assertThat(cloneUltimaDisciplina.getNome()).isEqualTo("Libras");
		assertThat(cloneUltimaDisciplina.getOptativa()).isTrue();
	}
	
	@Test
	public void testSomarMediasComReduce() {
		Double somaDasMedias = aluno.getDisciplinas().stream().reduce(0.0, (sum, d) -> sum += d.buscarMedia(), (sum1, sum2) -> sum1 + sum2).doubleValue();
		
		assertThat(somaDasMedias).isEqualTo(28.625);
	}
	
	@Test
	public void testBuscarDadosDeNotasDoAluno() {
		
		Double menorMedia = aluno.getDisciplinas().stream().mapToDouble(Disciplina::buscarMedia).min().getAsDouble();
		Double maiorMedia = aluno.getDisciplinas().stream().mapToDouble(Disciplina::buscarMedia).max().getAsDouble();
		Long quantidadeDeMedias = aluno.getDisciplinas().stream().mapToDouble(Disciplina::buscarMedia).count();
		Double somaDasMedias = aluno.getDisciplinas().stream().mapToDouble(Disciplina::buscarMedia).sum();
		
		assertThat(maiorMedia).isEqualTo(7.5);
		assertThat(menorMedia).isEqualTo(6.75);
		assertThat(somaDasMedias).isEqualTo(28.625);
		assertThat(quantidadeDeMedias).isEqualTo(4L);
	}
	
	@Test
	public void testBuscarDadosDeNotasDoAlunoComEstatisticas() {
		
		DoubleSummaryStatistics estatisticas = aluno.getDisciplinas().stream().mapToDouble(Disciplina::buscarMedia).summaryStatistics();
		
		Double menorMedia = estatisticas.getMin();
		Double maiorMedia = estatisticas.getMax();
		Long quantidadeDeMedias = estatisticas.getCount();
		Double somaDasMedias = estatisticas.getSum();
		
		assertThat(maiorMedia).isEqualTo(7.5);
		assertThat(menorMedia).isEqualTo(6.75);
		assertThat(somaDasMedias).isEqualTo(28.625);
		assertThat(quantidadeDeMedias).isEqualTo(4L);
		
	}
}
