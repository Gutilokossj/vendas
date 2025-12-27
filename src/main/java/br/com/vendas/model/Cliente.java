package br.com.vendas.model;

import br.com.vendas.model.base.EntidadeBase;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import java.io.Serial;
import java.io.Serializable;

@Entity
@Table(name = "cliente")
public class Cliente extends EntidadeBase implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @NotBlank(message = "Nome é obrigatório")
    @Column(nullable = false, length = 60)
    private String nome;

    @NotBlank(message = "CPF ou CNPJ é obrigatório")
    @Column(nullable = false, length = 18, unique = true)
    private String documento;

    @NotBlank(message = "IE é obrigatório")
    @Column(nullable = false, length = 14)
    private String inscricaoEstadual;

    @NotBlank(message = "CEP é obrigatório")
    @Column(nullable = false, length = 9)
    private String cep;

    @NotBlank(message = "Endereço é obrigatório")
    @Column(nullable = false, length = 60)
    private String logradouro;

    @NotBlank(message = "Número do endereço é obrigatório")
    @Column(nullable = false, length = 6)
    private String numero;

    @NotBlank(message = "Bairro é obrigatório")
    @Column(nullable = false, length = 60)
    private String bairro;

    @Column(length = 60)
    private String complemento;

    @NotBlank(message = "Cidade é obrigatória")
    @Column(nullable = false, length = 60)
    private String cidade;

    @NotBlank(message = "Estado é obrigatório")
    @Column(nullable = false, length = 20)
    private String estado;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDocumento() {
        return documento;
    }

    public void setDocumento(String documento) {
        this.documento = documento;
    }

    public String getInscricaoEstadual() {
        return inscricaoEstadual;
    }

    public void setInscricaoEstadual(String inscricaoEstadual) {
        this.inscricaoEstadual = inscricaoEstadual;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public String getLogradouro() {
        return logradouro;
    }

    public void setLogradouro(String logradouro) {
        this.logradouro = logradouro;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getComplemento() {
        return complemento;
    }

    public void setComplemento(String complemento) {
        this.complemento = complemento;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}
