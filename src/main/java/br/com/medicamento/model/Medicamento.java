package br.com.medicamento.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

import javax.persistence.*;
import javax.validation.constraints.*;


//Entity informa que nossa classe/modelo se tornará uma tabela no banco de dados!
//Table não é obrigatória, mas serve para definir o nome da tabela, ajuda muito manter o padrao!
@Entity
@Table(name="medicamento")
public class Medicamento implements Serializable, Base{
	
	 /*Serve para permitir que o objeto seja transformado em bytes e guardado na sessão, enviado pela rede, ou gravado em arquivo.
	 * No JSF, principalmente, ele é necessário porque:

		✔ Beans @SessionScoped precisam ser serializáveis
		
		O próprio JSF salva o bean na sessão, e a sessão pode ser:
		
		armazenada no disco
		
		replicada entre servidores
		
		compactada
		
		restaurada depois
		
		Por isso todo bean de sessão deve implementar Serializable.
		
		E por tabela, toda entidade dentro do bean também deve ser.
			*/
	private static final long serialVersionUID = 1L;
	
	//ID informa que nosso atributo será o identificador da tabela, chave primária da tabela
	//GeneratedValue funciona como autoIncrement do SQL, conforme inserimos registros ele atribui automático o valor do ID
	//Column assim como table, serve definir o nome da coluna no banco de dados se quiser!
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="codigo")
	private Long id;
	
	@NotBlank(message = "Medicamento é obrigatório, não pode ser nulo")
	@Column(length = 120)
	private String nome;
	
	@NotBlank(message = "Apresentação é obrigatório, não pode ser nulo")
	@Column(length = 255)
	private String apresentacao; //Se vai ser 30 comprimidos, tantos ML, etc
	
	@NotNull(message = "Labotório é obrigatório")
    @Enumerated(EnumType.STRING)
	@Column(length = 120)
	private TipoLaboratorios laboratorio;
	
	@NotBlank(message = "Registro MS é obrigatório")
	@Column(length = 20)
	private String registro;
	
	@NotNull(message = "Preço é obrigatório")
    @DecimalMin(value = "0.01", inclusive = true, message = "Informe um valor positivo, acima de 0,01.")
    @Digits(integer = 8, fraction = 2, message = "Use no máximo duas casas decimais (ex.: 10,50).") //integer = 8 → permite no máximo 99.999.999, fraction = 2 → .99 total = 8 + 2 = 10 dígitos
	@Column(precision = 10, scale = 2) //precision = 10 (contando 2 depois da vírgula ali) → até 99.999.999,99 e scale = 2 → duas casas decimais.
	private BigDecimal preco; //Representa o preço do medicamento
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getApresentacao() {
		return apresentacao;
	}
	public void setApresentacao(String apresentacao) {
		this.apresentacao = apresentacao;
	}

    public TipoLaboratorios getLaboratorio() {
        return laboratorio;
    }
    public void setLaboratorio(TipoLaboratorios laboratorio) {
        this.laboratorio = laboratorio;
    }

    public String getRegistro() {
		return registro;
	}
	public void setRegistro(String registro) {
		this.registro = registro;
	}
	public BigDecimal getPreco() {
		return preco;
	}
	public void setPreco(BigDecimal preco) {
		this.preco = preco;
	}
	
	//Serve para diferencias nossos objetos!
	@Override
	public int hashCode() {
		return Objects.hash(id);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Medicamento other = (Medicamento) obj;
		return Objects.equals(id, other.id);
	}
	
}
