/*SCRIPT PARA CREAR*/

/* ============================================================
   TABLA ESTILO
   ============================================================ */

CREATE TABLE Estilo (
    pt_cod NUMBER(10) NOT NULL,
    pt_estilo_nombre VARCHAR2(50) NOT NULL,
    pt_densidad_ini_minima NUMBER(10,4) NOT NULL,
    pt_densidad_ini_maxima NUMBER(10,4) NOT NULL,
    pt_densidad_fin_minima NUMBER(10,4) NOT NULL,
    pt_densidad_fin_maxima NUMBER(10,4) NOT NULL,
    pt_alcohol_minimo NUMBER(10,4) NOT NULL,
    pt_alcohol_maximo NUMBER(10,4) NOT NULL,
    pt_amargor_minimo NUMBER(10,4) NOT NULL,
    pt_amargor_maximo NUMBER(10,4) NOT NULL,
    pt_color_minimo NUMBER(10,4) NOT NULL,
    pt_color_maximo NUMBER(10,4) NOT NULL,

    CONSTRAINT pk_estilo PRIMARY KEY (pt_cod)
);


/* ============================================================
   TABLA HERVIDO
   ============================================================ */

CREATE TABLE Hervido (
    h_cod NUMBER(10) NOT NULL,
    h_tiempo_ebullicion NUMBER(10) NOT NULL,

    CONSTRAINT pk_hervido PRIMARY KEY (h_cod)
);


/* ============================================================
   TABLA WHIRPOOL
   ============================================================ */

CREATE TABLE Whirpool (
    w_cod NUMBER(10) NOT NULL,
    w_tiempo_inicio NUMBER(10,4) NOT NULL,
    w_tiempo_centrifugado NUMBER(10,4) NOT NULL,
    w_tiempo_reposo NUMBER(10,4) NOT NULL,

    CONSTRAINT pk_whirpool PRIMARY KEY (w_cod)
);


/* ============================================================
   TABLA FERMENTACION
   ============================================================ */

CREATE TABLE Fermentacion (
    f_cod NUMBER(10) NOT NULL,

    CONSTRAINT pk_fermentacion PRIMARY KEY (f_cod)
);


/* ============================================================
   TABLA DRY HOPPING
   ============================================================ */

CREATE TABLE Dry_Hopping (
    f_dry_cod NUMBER(10) NOT NULL,
    tiempo_contacto NUMBER(10,4) NOT NULL,
    temperatura NUMBER(10,4) NOT NULL,

    CONSTRAINT pk_dry_hopping PRIMARY KEY (f_dry_cod)
);


/* ============================================================
   TABLA CARBONATACION
   ============================================================ */

CREATE TABLE Carbonatacion (
    car_cod NUMBER(10) NOT NULL,
    car_vol_obj_CO2 NUMBER(10,4) NOT NULL,
    car_metodo_empleado VARCHAR2(60) NOT NULL,

    CONSTRAINT pk_carbonatacion PRIMARY KEY (car_cod)
);


/* ============================================================
   TABLA MACERACION
   ============================================================ */

CREATE TABLE Maceracion (
    m_cod NUMBER(10) NOT NULL,
    m_relacion_agua_grano NUMBER(10,4) NOT NULL,

    CONSTRAINT pk_maceracion PRIMARY KEY (m_cod)
);


/* ============================================================
   TABLA RECETAS
   ============================================================ */

CREATE TABLE Recetas (
    r_cod NUMBER(10) NOT NULL,
    r_nombre VARCHAR2(50) NOT NULL,
    r_codigo VARCHAR2(50) NOT NULL,
    r_version NUMBER(10) NOT NULL,
    r_batch_size NUMBER(10,4) NOT NULL,
    r_eficiencia_size NUMBER(10,4) NOT NULL,
    r_autor VARCHAR2(50) NOT NULL,
    r_fecha_c DATE NOT NULL,

    Estilo_pt_cod NUMBER(10) NOT NULL,
    Hervido_h_cod NUMBER(10) NOT NULL,
    Whirpool_w_cod NUMBER(10) NOT NULL,
    Fermentacion_f_cod NUMBER(10) NOT NULL,
    Dry_Hopping_f_dry_cod NUMBER(10),
    Carbonatacion_car_cod NUMBER(10) NOT NULL,
    Maceracion_m_cod NUMBER(10) NOT NULL,

    CONSTRAINT pk_recetas PRIMARY KEY (r_cod),

    CONSTRAINT uk_recetas_codigo
        UNIQUE (r_codigo),

    CONSTRAINT uk_recetas_dry_hopping
        UNIQUE (Dry_Hopping_f_dry_cod),

    CONSTRAINT fk_recetas_estilo
        FOREIGN KEY (Estilo_pt_cod)
        REFERENCES Estilo (pt_cod),

    CONSTRAINT fk_recetas_hervido
        FOREIGN KEY (Hervido_h_cod)
        REFERENCES Hervido (h_cod),

    CONSTRAINT fk_recetas_whirpool
        FOREIGN KEY (Whirpool_w_cod)
        REFERENCES Whirpool (w_cod),

    CONSTRAINT fk_recetas_fermentacion
        FOREIGN KEY (Fermentacion_f_cod)
        REFERENCES Fermentacion (f_cod),

    CONSTRAINT fk_recetas_dry_hopping
        FOREIGN KEY (Dry_Hopping_f_dry_cod)
        REFERENCES Dry_Hopping (f_dry_cod),

    CONSTRAINT fk_recetas_carbonatacion
        FOREIGN KEY (Carbonatacion_car_cod)
        REFERENCES Carbonatacion (car_cod),

    CONSTRAINT fk_recetas_maceracion
        FOREIGN KEY (Maceracion_m_cod)
        REFERENCES Maceracion (m_cod)
);


/* ============================================================
   TABLA LOTES
   ============================================================ */

CREATE TABLE Lotes (
    lot_cod NUMBER(10) NOT NULL,
    lot_volumen_total NUMBER(10,4) NOT NULL,
    lot_fecha_vecimiento DATE NOT NULL,

    fecha_maceracion DATE,
    cantidad_maceracion NUMBER(10,4),

    fecha_hervido DATE,
    cantidad_hervido NUMBER(10,4),

    fecha_whirpool DATE,
    cantidad_whirpool NUMBER(10,4),

    fecha_fermentacion DATE,
    cantidad_fermentacion NUMBER(10,4),

    fecha_dryhopping DATE,
    cantidad_dryhopping NUMBER(10,4),

    fecha_carbonatacion DATE,
    cantidad_carbonatacion NUMBER(10,4),

    lot_fecha_fin_preparado DATE,

    Recetas_r_cod NUMBER(10) NOT NULL,

    CONSTRAINT pk_lotes PRIMARY KEY (lot_cod),

    CONSTRAINT fk_lotes_recetas
        FOREIGN KEY (Recetas_r_cod)
        REFERENCES Recetas (r_cod)
);


/* ============================================================
   TABLA MACERACION_TEMPERATURA
   ============================================================ */

CREATE TABLE Maceracion_Temperatura (
    temperatura NUMBER(10,4) NOT NULL,
    Maceracion_m_cod NUMBER(10) NOT NULL,

    CONSTRAINT pk_maceracion_temperatura
        PRIMARY KEY (temperatura, Maceracion_m_cod),

    CONSTRAINT fk_mac_temp_maceracion
        FOREIGN KEY (Maceracion_m_cod)
        REFERENCES Maceracion (m_cod)
);


/* ============================================================
   TABLA HERVIDO_CRONOGRAMA
   ============================================================ */

CREATE TABLE Hervido_Cronograma (
    cronograma_minuto NUMBER(10) NOT NULL,
    cronograma_ingrediente VARCHAR2(60) NOT NULL,
    Hervido_h_cod NUMBER(10) NOT NULL,

    CONSTRAINT pk_hervido_cronograma
        PRIMARY KEY (
            Hervido_h_cod,
            cronograma_minuto,
            cronograma_ingrediente
        ),

    CONSTRAINT fk_hervido_cronograma
        FOREIGN KEY (Hervido_h_cod)
        REFERENCES Hervido (h_cod)
);


/* ============================================================
   TABLA ETAPA_FERMENTACION
   ============================================================ */

CREATE TABLE Etapa_Fermentacion (
    dia_numero DATE NOT NULL,
    temperatura_dia NUMBER(10,4) NOT NULL,
    f_nombre_etapa VARCHAR2(60) NOT NULL,
    Fermentacion_f_cod NUMBER(10) NOT NULL,

    CONSTRAINT pk_etapa_fermentacion
        PRIMARY KEY (
            dia_numero,
            temperatura_dia,
            f_nombre_etapa,
            Fermentacion_f_cod
        ),

    CONSTRAINT fk_etapa_fermentacion
        FOREIGN KEY (Fermentacion_f_cod)
        REFERENCES Fermentacion (f_cod)
);


/* ============================================================
   TABLA GRANOS
   ============================================================ */

CREATE TABLE Granos (
    g_cod NUMBER(10) NOT NULL,
    g_nombre VARCHAR2(45) NOT NULL,
    g_tipo VARCHAR2(45) NOT NULL,
    g_porcentaje NUMBER(10,4) NOT NULL,
    g_peso NUMBER(10,4) NOT NULL,
    g_potencial_extraccion VARCHAR2(45) NOT NULL,
    g_color VARCHAR2(20) NOT NULL,

    CONSTRAINT pk_granos PRIMARY KEY (g_cod)
);


/* ============================================================
   TABLA LUPULOS
   ============================================================ */

CREATE TABLE Lupulos (
    lu_cod NUMBER(10) NOT NULL,
    lu_variedad VARCHAR2(45) NOT NULL,
    lu_anio_cosecha NUMBER(4) NOT NULL,
    lu_formato VARCHAR2(20) NOT NULL,
    lu_porcentaje_alfa_acido NUMBER(10,4) NOT NULL,
    lu_cantidad NUMBER(10,4) NOT NULL,

    lu_proposito VARCHAR2(20) NOT NULL,

    CONSTRAINT pk_lupulos PRIMARY KEY (lu_cod),

    CONSTRAINT ck_lupulos_proposito
        CHECK (lu_proposito IN ('Amargor', 'Sabor', 'Aroma'))
);


/* ============================================================
   TABLA LEVADURAS
   ============================================================ */

CREATE TABLE Levaduras (
    le_cod NUMBER(10) NOT NULL,
    le_cepa VARCHAR2(45) NOT NULL,

    le_formato_lev VARCHAR2(20) NOT NULL,

    le_cantidad_lev NUMBER(10,4) NOT NULL,
    le_tasa_inoculacion NUMBER(10,4) NOT NULL,

    CONSTRAINT pk_levaduras PRIMARY KEY (le_cod),

    CONSTRAINT ck_levaduras_formato
        CHECK (le_formato_lev IN ('Seca', 'Líquida'))
);


/* ============================================================
   TABLA ADITIVOS
   ============================================================ */

CREATE TABLE Aditivos (
    a_cod NUMBER(10) NOT NULL,
    ad_nombre_aditivo VARCHAR2(45) NOT NULL,
    ad_tipo_aditivo VARCHAR2(45) NOT NULL,
    ad_cantidad_aditivo NUMBER(10,4) NOT NULL,

    CONSTRAINT pk_aditivos PRIMARY KEY (a_cod)
);


/* ============================================================
   TABLA LLEVA_GRANOS
   ============================================================ */

CREATE TABLE Lleva_Granos (
    Recetas_r_cod NUMBER(10) NOT NULL,
    Granos_g_cod NUMBER(10) NOT NULL,

    CONSTRAINT pk_lleva_granos
        PRIMARY KEY (Recetas_r_cod, Granos_g_cod),

    CONSTRAINT fk_lleva_granos_recetas
        FOREIGN KEY (Recetas_r_cod)
        REFERENCES Recetas (r_cod),

    CONSTRAINT fk_lleva_granos_granos
        FOREIGN KEY (Granos_g_cod)
        REFERENCES Granos (g_cod)
);


/* ============================================================
   TABLA LLEVA_LUPULOS
   ============================================================ */

CREATE TABLE Lleva_Lupulos (
    Recetas_r_cod NUMBER(10) NOT NULL,
    Lupulos_lu_cod NUMBER(10) NOT NULL,

    CONSTRAINT pk_lleva_lupulos
        PRIMARY KEY (Lupulos_lu_cod, Recetas_r_cod),

    CONSTRAINT fk_lleva_lupulos_recetas
        FOREIGN KEY (Recetas_r_cod)
        REFERENCES Recetas (r_cod),

    CONSTRAINT fk_lleva_lupulos_lupulos
        FOREIGN KEY (Lupulos_lu_cod)
        REFERENCES Lupulos (lu_cod)
);


/* ============================================================
   TABLA LLEVA_LEVADURAS
   ============================================================ */

CREATE TABLE Lleva_Levaduras (
    Recetas_r_cod NUMBER(10) NOT NULL,
    Levaduras_le_cod NUMBER(10) NOT NULL,

    CONSTRAINT pk_lleva_levaduras
        PRIMARY KEY (Recetas_r_cod, Levaduras_le_cod),

    CONSTRAINT fk_lleva_levaduras_recetas
        FOREIGN KEY (Recetas_r_cod)
        REFERENCES Recetas (r_cod),

    CONSTRAINT fk_lleva_levaduras_levaduras
        FOREIGN KEY (Levaduras_le_cod)
        REFERENCES Levaduras (le_cod)
);


/* ============================================================
   TABLA LLEVA_ADITIVOS
   ============================================================ */

CREATE TABLE Lleva_Aditivos (
    Recetas_r_cod NUMBER(10) NOT NULL,
    Aditivos_a_cod NUMBER(10) NOT NULL,

    CONSTRAINT pk_lleva_aditivos
        PRIMARY KEY (Recetas_r_cod, Aditivos_a_cod),

    CONSTRAINT fk_lleva_aditivos_recetas
        FOREIGN KEY (Recetas_r_cod)
        REFERENCES Recetas (r_cod),

    CONSTRAINT fk_lleva_aditivos_aditivos
        FOREIGN KEY (Aditivos_a_cod)
        REFERENCES Aditivos (a_cod)
);


/* ============================================================
   TABLA INVENTARIO
   ============================================================ */

CREATE TABLE Inventario (
    i_cod NUMBER(10) NOT NULL,
    i_litros_totales NUMBER(10,4) NOT NULL,

    i_estado_etapa VARCHAR2(30) NOT NULL,

    CONSTRAINT pk_inventario PRIMARY KEY (i_cod),

    CONSTRAINT ck_inventario_estado
        CHECK (
            i_estado_etapa IN (
                'Fermentación/Maduración',
                'Para Envasar',
                'Envasados'
            )
        )
);


/* ============================================================
   TABLA BARRILES
   ============================================================ */

CREATE TABLE Barriles (
    b_cod NUMBER(10) NOT NULL,
    b_capacidad NUMBER(10,4) NOT NULL,

    b_tipo_conector VARCHAR2(20) NOT NULL,

    b_estado_operativo VARCHAR2(30) NOT NULL,

    b_ubicacion_actual VARCHAR2(50) NOT NULL,

    Inventario_i_cod NUMBER(10) NOT NULL,

    CONSTRAINT pk_barriles PRIMARY KEY (b_cod),

    CONSTRAINT ck_barriles_conector
        CHECK (
            b_tipo_conector IN (
                'G',
                'A',
                'S',
                'Micromatic'
            )
        ),

    CONSTRAINT ck_barriles_estado
        CHECK (
            b_estado_operativo IN (
                'Sucio/Retornado',
                'Lavado/Satinizado',
                'Lleno',
                'En Uso',
                'En Mantenimiento',
                'Extraviado/Baja'
            )
        ),

    CONSTRAINT fk_barriles_inventario
        FOREIGN KEY (Inventario_i_cod)
        REFERENCES Inventario (i_cod)
);


/* ============================================================
   TABLA CLIENTES
   ============================================================ */

CREATE TABLE Clientes (
    cl_cod NUMBER(10) NOT NULL,
    cl_nombre VARCHAR2(45) NOT NULL,
    cl_direccion VARCHAR2(100) NOT NULL,
    cl_telefono VARCHAR2(20) NOT NULL,

    CONSTRAINT pk_clientes PRIMARY KEY (cl_cod)
);


/* ============================================================
   TABLA ENTREGAS
   ============================================================ */

CREATE TABLE Entregas (
    en_cod NUMBER(10) NOT NULL,
    en_remito_factura VARCHAR2(45) NOT NULL,
    en_fecha_entrega DATE NOT NULL,
    en_tiempo_permanencia NUMBER(10,4) NOT NULL,
    en_fecha_devolucion DATE,

    Barriles_b_cod NUMBER(10) NOT NULL,
    Clientes_cl_cod NUMBER(10) NOT NULL,

    CONSTRAINT pk_entregas
        PRIMARY KEY (
            en_cod,
            Barriles_b_cod,
            Clientes_cl_cod
        ),

    CONSTRAINT fk_entregas_barriles
        FOREIGN KEY (Barriles_b_cod)
        REFERENCES Barriles (b_cod),

    CONSTRAINT fk_entregas_clientes
        FOREIGN KEY (Clientes_cl_cod)
        REFERENCES Clientes (cl_cod)
);


/* ============================================================
   TABLA MOVIMIENTOS
   ============================================================ */

CREATE TABLE Movimientos (
    mov_cod NUMBER(10) NOT NULL,
    mov_fecha DATE NOT NULL,
    mov_hora NUMBER(10) NOT NULL,
    mov_punto_origen VARCHAR2(45) NOT NULL,
    mov_punto_destino VARCHAR2(45) NOT NULL,
    mov_responsable VARCHAR2(45) NOT NULL,

    Barriles_b_cod NUMBER(10) NOT NULL,

    CONSTRAINT pk_movimientos PRIMARY KEY (mov_cod),

    CONSTRAINT fk_movimientos_barriles
        FOREIGN KEY (Barriles_b_cod)
        REFERENCES Barriles (b_cod)
);


/* ============================================================
   TABLA MANTENIMIENTO
   ============================================================ */

CREATE TABLE Mantenimiento (
    man_cod NUMBER(10) NOT NULL,
    man_fecha_lavado DATE,
    man_cambio_de_sello VARCHAR2(45),
    man_prueba_presion VARCHAR2(45),

    Barriles_b_cod NUMBER(10) NOT NULL,

    CONSTRAINT pk_mantenimiento PRIMARY KEY (man_cod),

    CONSTRAINT fk_mantenimiento_barriles
        FOREIGN KEY (Barriles_b_cod)
        REFERENCES Barriles (b_cod)
);


/* ============================================================
   TABLA ESTA_COMPUESTO
   ============================================================ */

CREATE TABLE Esta_Compuesto (
    lot_fecha_inicio DATE NOT NULL,
    lot_fecha_fin DATE,

    Lotes_lot_cod NUMBER(10) NOT NULL,
    Barriles_b_cod NUMBER(10) NOT NULL,

    CONSTRAINT pk_esta_compuesto
        PRIMARY KEY (
            Lotes_lot_cod,
            Barriles_b_cod
        ),

    CONSTRAINT fk_esta_compuesto_lotes
        FOREIGN KEY (Lotes_lot_cod)
        REFERENCES Lotes (lot_cod),

    CONSTRAINT fk_esta_compuesto_barriles
        FOREIGN KEY (Barriles_b_cod)
        REFERENCES Barriles (b_cod)
);


/* ============================================================
   ÍNDICES
   ============================================================ */

CREATE INDEX idx_lotes_recetas
    ON Lotes (Recetas_r_cod);

CREATE INDEX idx_mac_temp_maceracion
    ON Maceracion_Temperatura (Maceracion_m_cod);

CREATE INDEX idx_etapa_fermentacion
    ON Etapa_Fermentacion (Fermentacion_f_cod);

CREATE INDEX idx_barriles_inventario
    ON Barriles (Inventario_i_cod);

CREATE INDEX idx_entregas_barriles
    ON Entregas (Barriles_b_cod);

CREATE INDEX idx_entregas_clientes
    ON Entregas (Clientes_cl_cod);

CREATE INDEX idx_movimientos_barriles
    ON Movimientos (Barriles_b_cod);

CREATE INDEX idx_mantenimiento_barriles
    ON Mantenimiento (Barriles_b_cod);

CREATE INDEX idx_esta_compuesto_barriles
    ON Esta_Compuesto (Barriles_b_cod);


COMMIT;