-- Migration: Criação da tabela de veículos
-- Descrição: Tabela principal para armazenar informações dos veículos cadastrados
-- Criação da tabela vehicles
CREATE TABLE vehicles
(
    id          UUID                  DEFAULT gen_random_uuid() PRIMARY KEY,
    vehicle     VARCHAR(255) NOT NULL,
    brand       VARCHAR(100) NOT NULL,
    YEAR        INTEGER      NOT NULL,
    description TEXT,
    sold        BOOLEAN      NOT NULL DEFAULT FALSE,
    color       VARCHAR(100) NOT NULL,
    deleted     BOOLEAN               DEFAULT FALSE,
    created_at  TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at  TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT check_year_valid CHECK (
        YEAR >= 1886
            AND YEAR <= EXTRACT(
                                YEAR
                                FROM
                                CURRENT_DATE
                        ) + 1
        ),
    CONSTRAINT check_brand_not_empty CHECK (TRIM(brand) <> ''),
    CONSTRAINT check_vehicle_not_empty CHECK (TRIM(vehicle) <> '')
);

CREATE INDEX idx_vehicles_brand ON vehicles (brand);
CREATE INDEX idx_vehicles_year ON vehicles (YEAR);
CREATE INDEX idx_vehicles_sold ON vehicles (sold);
CREATE INDEX idx_vehicles_created_at ON vehicles (created_at);
CREATE INDEX idx_vehicles_year_sold ON vehicles (YEAR, sold);

COMMENT ON TABLE vehicles IS 'Tabela de cadastro de veículos';
COMMENT ON COLUMN vehicles.id IS 'Identificador único do veículo';
COMMENT ON COLUMN vehicles.vehicle IS 'Nome ou modelo do veículo';
COMMENT ON COLUMN vehicles.brand IS 'Marca ou fabricante do veículo';
COMMENT ON COLUMN vehicles.year IS 'Ano de fabricação do veículo';
COMMENT ON COLUMN vehicles.description IS 'Descrição detalhada das características do veículo';
COMMENT ON COLUMN vehicles.sold IS 'Indica se o veículo foi vendido (true) ou está disponível (false)';
COMMENT ON COLUMN vehicles.created_at IS 'Data e hora de criação do registro';
COMMENT ON COLUMN vehicles.updated_at IS 'Data e hora da última atualização do registro';
