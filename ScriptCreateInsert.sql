-- Criação do schema
CREATE SCHEMA IF NOT EXISTS imobiliaria_familia;
USE imobiliaria_familia;

-- Tabela CLIENTE (antes era USUARIO)
CREATE TABLE IF NOT EXISTS CLIENTE (
    id_cliente INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL,
    telefone VARCHAR(20)
);

-- Tabela IMOVEL (antes era LIVRO)
CREATE TABLE IF NOT EXISTS IMOVEL (
    id_imovel INT AUTO_INCREMENT PRIMARY KEY,
    endereco VARCHAR(255) NOT NULL,
    tipo VARCHAR(100), -- ex: apartamento, casa, sala comercial
    area_m2 DECIMAL(10,2),
    quartos INT,
    banheiros INT,
    vagas_garagem INT,
    observacoes TEXT
);

-- Tabela CONTRATO_ALUGUEL (antes era EMPRESTIMO)
CREATE TABLE IF NOT EXISTS CONTRATO_ALUGUEL (
    id_contrato INT AUTO_INCREMENT PRIMARY KEY,
    id_cliente INT NOT NULL,
    id_imovel INT NOT NULL,
    valor_aluguel DECIMAL(10,2) NOT NULL,
    data_inicio DATE NOT NULL,
    data_fim DATE,
    data_assinatura DATE NOT NULL,
    observacoes TEXT,
    FOREIGN KEY (id_cliente) REFERENCES CLIENTE(id_cliente),
    FOREIGN KEY (id_imovel) REFERENCES IMOVEL(id_imovel)
);