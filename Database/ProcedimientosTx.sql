CREATE OR REPLACE PROCEDURE movimiento_alta(fecha IN DATE, hora IN NUMBER, lugar_origen IN VARCHAR2, lugar_destino IN VARCHAR2, responsable IN VARCHAR2, barril_cod IN NUMBER)
AS existebarril NUMBER;
BEGIN 
    SELECT COUNT(*) INTO existebarril
    FROM Barriles
    WHERE b_cod = barril_cod;    
    IF existebarril = 0 THEN
        RAISE_APPLICATION_ERROR(-20001, 'El Barril NO existe, haciendo rollback');
    END IF;
    INSERT INTO Movimientos(mov_fecha, mov_hora, mov_punto_origen, mov_punto_destino, mov_responsable, Barriles_b_cod)
    VALUES (fecha, hora, lugar_origen, lugar_destino, responsable, barril_cod);
    COMMIT;
EXCEPTION
    WHEN OTHERS THEN
        ROLLBACK;
        RAISE;
END;

CREATE OR REPLACE PROCEDURE barril_alta(capacidad IN NUMBER, conector IN VARCHAR2, estado IN VARCHAR2, ubicacion IN VARCHAR2, inventario_cod IN NUMBER, lote_barril_cod IN NUMBER, fecha_inicio IN DATE)
AS existe_lote NUMBER; 
barril_cod NUMBER;
BEGIN
    SELECT COUNT(*) INTO existe_lote
    FROM Lotes
    WHERE lot_cod = lote_barril_cod;
    IF existe_lote = 0 THEN
        RAISE_APPLICATION_ERROR(-20001, 'El Lote NO se existe o NO esta cargado, haciendo rollback');
    END IF;
    INSERT INTO Barriles (b_capacidad, b_tipo_conector, b_estado_operativo, b_ubicacion_actual, Inventario_i_cod)
    VALUES (capacidad, conector, estado, ubicacion, inventario_cod)
    RETURNING b_cod INTO barril_cod;
    INSERT INTO Esta_Compuesto (lot_fecha_inicio, Lotes_lot_cod, Barriles_b_cod)
    VALUES (fecha_inicio, lote_barril_cod, barril_cod);
    COMMIT;
EXCEPTION
    WHEN OTHERS THEN
        ROLLBACK;
        RAISE;
END;

CREATE OR REPLACE PROCEDURE lote_alta (volumen_total IN NUMBER, fecha_vencimiento IN DATE, fecha_maceracion IN DATE, cant_maceracion IN NUMBER, fecha_hervido IN DATE, cant_hervido IN NUMBER,
fecha_whirpool IN DATE, cant_whirpool IN NUMBER, fecha_fermentacion IN DATE, cant_fermentacion IN NUMBER, fecha_dryhopping IN DATE, cant_dryhopping IN NUMBER, fecha_carbonatacion IN DATE, 
cant_carbonatacion IN NUMBER, fecha_fin_preparado IN DATE, receta_variable_cod IN NUMBER, barril_variable_cod IN NUMBER, fecha_inicio_comp IN DATE, barril_cap IN NUMBER, barril_conector IN VARCHAR2,
barril_estado IN VARCHAR2, barril_ubi IN VARCHAR2, barril_inv_cod IN NUMBER
) AS
    existe_receta NUMBER;
    existe_barril NUMBER;
    lote_cod NUMBER;
    barril_final NUMBER := barril_variable_cod;
BEGIN
    SELECT COUNT(*) INTO existe_receta FROM Recetas WHERE r_cod = receta_variable_cod;
    IF existe_receta = 0 THEN
        RAISE_APPLICATION_ERROR(-20003, 'La Receta NO se encuentra cargada');
    END IF;
    INSERT INTO Lotes (lot_volumen_total, lot_fecha_vecimiento, fecha_maceracion, cantidad_maceracion, fecha_hervido, cantidad_hervido, fecha_whirpool, cantidad_whirpool, 
    fecha_fermentacion, cantidad_fermentacion, fecha_dryhopping, cantidad_dryhopping, fecha_carbonatacion, cantidad_carbonatacion, lot_fecha_fin_preparado, Recetas_r_cod) VALUES 
    (volumen_total, fecha_vencimiento, fecha_maceracion, cant_maceracion, fecha_hervido, cant_hervido, fecha_whirpool, cant_whirpool, fecha_fermentacion, cant_fermentacion, fecha_dryhopping, cant_dryhopping, 
    fecha_carbonatacion, cant_carbonatacion, fecha_fin_preparado, receta_variable_cod) RETURNING lot_cod INTO lote_cod;
    SELECT COUNT(*) INTO existe_barril FROM Barriles WHERE b_cod = barril_final;
    IF existe_barril = 0 THEN
        INSERT INTO Barriles (b_capacidad, b_tipo_conector, b_estado_operativo, b_ubicacion_actual, Inventario_i_cod)
        VALUES (barril_cap, barril_conector, barril_estado, barril_ubi, barril_inv_cod)
        RETURNING b_cod INTO barril_final;
    END IF;
    INSERT INTO Esta_Compuesto (lot_fecha_inicio, Lotes_lot_cod, Barriles_b_cod)
    VALUES (fecha_inicio_comp, lote_cod, barril_final);
    COMMIT;
EXCEPTION
    WHEN OTHERS THEN
        ROLLBACK;
        RAISE;
END;


CREATE OR REPLACE PROCEDURE receta_alta (
    -- Atributos de la Receta
    p_r_nombre               IN  VARCHAR2, 
    p_r_codigo               IN  VARCHAR2, 
    p_r_version              IN  NUMBER, 
    p_r_batch_size           IN  NUMBER, 
    p_r_eficiencia_size      IN  NUMBER, 
    p_r_autor                IN  VARCHAR2,
    p_r_fecha_c              IN  DATE, 
    p_estilo_pt_cod          IN  NUMBER, 
    p_hervido_h_cod          IN  NUMBER, 
    p_whirpool_w_cod         IN  NUMBER, 
    p_fermentacion_f_cod     IN  NUMBER, 
    p_dry_hopping_f_dry_cod  IN  NUMBER, 
    p_carbonatacion_car_cod  IN  NUMBER, 
    p_maceracion_m_cod       IN  NUMBER,
    
    -- Atributos de Granos
    p_g_cod                  IN  NUMBER,
    p_g_nombre               IN  VARCHAR2,
    p_g_tipo                 IN  VARCHAR2,
    p_g_porcentaje           IN  NUMBER,
    p_g_peso                 IN  NUMBER,
    p_g_potencial_extraccion IN  VARCHAR2,
    p_g_color                IN  VARCHAR2,
    
    -- Atributos de Lúpulos
    p_lu_cod                 IN  NUMBER,
    p_lu_variedad            IN  VARCHAR2,
    p_lu_anio_cosecha        IN  NUMBER,
    p_lu_formato             IN  VARCHAR2,
    p_lu_porcentaje_alfa_acido IN NUMBER,
    p_lu_cantidad            IN  NUMBER,
    p_lu_proposito           IN  VARCHAR2,
    
    -- Atributos de Levaduras
    p_le_cod                 IN  NUMBER,
    p_le_cepa                IN  VARCHAR2,
    p_le_formato_lev         IN  VARCHAR2,
    p_le_cantidad_lev        IN  NUMBER,
    p_le_tasa_inoculacion    IN  NUMBER,
    
    -- Atributos de Aditivos
    p_ad_cod                 IN  NUMBER,
    p_ad_nombre_aditivo      IN  VARCHAR2,
    p_ad_tipo_aditivo        IN  VARCHAR2,
    p_ad_cantidad_aditivo    IN  NUMBER,
    
    -- Parámetro de salida
    p_r_cod_id               OUT NUMBER
) AS
    v_g_cod  NUMBER;
    v_lu_cod NUMBER;
    v_le_cod NUMBER;
    v_a_cod  NUMBER;
BEGIN
    -- 1. Validación previa delegada al procedimiento de validación
    receta_alta_vld(
        p_r_nombre               => p_r_nombre,
        p_r_codigo               => p_r_codigo, 
        p_r_version              => p_r_version,
        p_r_batch_size           => p_r_batch_size,
        p_r_eficiencia_size      => p_r_eficiencia_size,
        p_r_autor                => p_r_autor,
        p_r_fecha_c              => p_r_fecha_c,
        p_estilo_pt_cod          => p_estilo_pt_cod,
        p_hervido_h_cod          => p_hervido_h_cod,
        p_whirpool_w_cod         => p_whirpool_w_cod,
        p_fermentacion_f_cod     => p_fermentacion_f_cod,
        p_dry_hopping_f_dry_cod  => p_dry_hopping_f_dry_cod,
        p_carbonatacion_car_cod  => p_carbonatacion_car_cod,
        p_maceracion_m_cod       => p_maceracion_m_cod,
        p_g_cod                  =>p_g_cod,
        p_g_nombre               => p_g_nombre,
        p_g_tipo                 => p_g_tipo,
        p_g_porcentaje           => p_g_porcentaje,
        p_g_peso                 => p_g_peso,
        p_g_potencial_extraccion => p_g_potencial_extraccion,
        p_g_color                => p_g_color,
        p_lu_cod                 => p_lu_cod,
        p_lu_variedad            => p_lu_variedad,
        p_lu_anio_cosecha        => p_lu_anio_cosecha,
        p_lu_formato             => p_lu_formato,
        p_lu_porcentaje_alfa_acido => p_lu_porcentaje_alfa_acido,
        p_lu_cantidad            => p_lu_cantidad,
        p_lu_proposito           => p_lu_proposito,
        p_le_cod                 => p_le_cod,
        p_le_cepa                => p_le_cepa,
        p_le_formato_lev         => p_le_formato_lev,
        p_le_cantidad_lev        => p_le_cantidad_lev,
        p_le_tasa_inoculacion    => p_le_tasa_inoculacion,
        p_ad_cod                 => p_ad_cod,
        p_ad_nombre_aditivo      => p_ad_nombre_aditivo,
        p_ad_tipo_aditivo        => p_ad_tipo_aditivo,
        p_ad_cantidad_aditivo    => p_ad_cantidad_aditivo
    );

    -- 2. Inserción de la Receta principal
    INSERT INTO Recetas (
        r_nombre, r_codigo, r_version, r_batch_size, r_eficiencia_size, 
        r_autor, r_fecha_c, Estilo_pt_cod, Hervido_h_cod, Whirpool_w_cod, 
        Fermentacion_f_cod, Dry_Hopping_f_dry_cod, Carbonatacion_car_cod, Maceracion_m_cod
    ) VALUES (
        p_r_nombre, p_r_codigo, p_r_version, p_r_batch_size, p_r_eficiencia_size, 
        p_r_autor, p_r_fecha_c, p_estilo_pt_cod, p_hervido_h_cod, p_whirpool_w_cod, 
        p_fermentacion_f_cod, p_dry_hopping_f_dry_cod, p_carbonatacion_car_cod, p_maceracion_m_cod
    )
    RETURNING r_cod INTO p_r_cod_id;

    -- 3. Inserción de los ingredientes en sus respectivas tablas maestras y captura de IDs
    if p_g_cod is null then
        INSERT INTO Granos (g_nombre, g_tipo, g_porcentaje, g_peso, g_potencial_extraccion, g_color)
        VALUES (p_g_nombre, p_g_tipo, p_g_porcentaje, p_g_peso, p_g_potencial_extraccion, p_g_color)
        RETURNING g_cod INTO v_g_cod;
    else
        v_g_cod := p_g_cod;
    end if;
    
    if p_lu_cod is null then
        INSERT INTO Lupulos (lu_variedad, lu_anio_cosecha, lu_formato, lu_porcentaje_alfa_acido, lu_cantidad, lu_proposito)
        VALUES (p_lu_variedad, p_lu_anio_cosecha, p_lu_formato, p_lu_porcentaje_alfa_acido, p_lu_cantidad, p_lu_proposito)
        RETURNING lu_cod INTO v_lu_cod;
    else
        v_lu_cod := p_lu_cod;
    end if;
    
    if p_le_cod is null then
        INSERT INTO Levaduras (le_cepa, le_formato_lev, le_cantidad_lev, le_tasa_inoculacion)
        VALUES (p_le_cepa, p_le_formato_lev, p_le_cantidad_lev, p_le_tasa_inoculacion)
        RETURNING le_cod INTO v_le_cod;
    else 
        v_le_cod := p_le_cod;
    end if;
    
    if p_ad_cod is null then
        INSERT INTO Aditivos (ad_nombre_aditivo, ad_tipo_aditivo, ad_cantidad_aditivo)
        VALUES (p_ad_nombre_aditivo, p_ad_tipo_aditivo, p_ad_cantidad_aditivo)
        RETURNING a_cod INTO v_a_cod;
    else
        v_ad_cod := p_ad_cod;
    end if;
    
    -- 4. Asociar los ingredientes a la receta en las tablas asociativas
    INSERT INTO Lleva_Granos (Recetas_r_cod, Granos_g_cod)
    VALUES (p_r_cod_id, v_g_cod);

    INSERT INTO Lleva_Lupulos (Recetas_r_cod, Lupulos_lu_cod)
    VALUES (p_r_cod_id, v_lu_cod);

    INSERT INTO Lleva_Levaduras (Recetas_r_cod, Levaduras_le_cod)
    VALUES (p_r_cod_id, v_le_cod);

    INSERT INTO Lleva_Aditivos (Recetas_r_cod, Aditivos_a_cod)
    VALUES (p_r_cod_id, v_a_cod);

END receta_alta;
/


CREATE OR REPLACE PROCEDURE receta_alta_vld (
    p_r_nombre               IN  VARCHAR2, 
    p_r_codigo               IN  VARCHAR2, 
    p_r_version              IN  NUMBER, 
    p_r_batch_size           IN  NUMBER, 
    p_r_eficiencia_size      IN  NUMBER, 
    p_r_autor                IN  VARCHAR2,
    p_r_fecha_c              IN  DATE, 
    p_estilo_pt_cod          IN  NUMBER, 
    p_hervido_h_cod          IN  NUMBER, 
    p_whirpool_w_cod         IN  NUMBER, 
    p_fermentacion_f_cod     IN  NUMBER, 
    p_dry_hopping_f_dry_cod  IN  NUMBER, 
    p_carbonatacion_car_cod  IN  NUMBER, 
    p_maceracion_m_cod       IN  NUMBER,
    p_g_cod                  IN  NUMBER,
    p_g_nombre               IN  VARCHAR2,
    p_g_tipo                 IN  VARCHAR2,
    p_g_porcentaje           IN  NUMBER,
    p_g_peso                 IN  NUMBER,
    p_g_potencial_extraccion IN  VARCHAR2,
    p_g_color                IN  VARCHAR2,
    p_lu_cod                 IN  NUMBER,
    p_lu_variedad            IN  VARCHAR2,
    p_lu_anio_cosecha        IN  NUMBER,
    p_lu_formato             IN  VARCHAR2,
    p_lu_porcentaje_alfa_acido IN NUMBER,
    p_lu_cantidad            IN  NUMBER,
    p_lu_proposito           IN  VARCHAR2,
    p_le_cod                 IN  NUMBER,
    p_le_cepa                IN  VARCHAR2,
    p_le_formato_lev         IN  VARCHAR2,
    p_le_cantidad_lev        IN  NUMBER,
    p_le_tasa_inoculacion    IN  NUMBER,
    p_ad_cod                 IN NUMBER,
    p_ad_nombre_aditivo      IN  VARCHAR2,
    p_ad_tipo_aditivo        IN  VARCHAR2,
    p_ad_cantidad_aditivo    IN  NUMBER
) AS
    CURSOR cr_cod IS 
        SELECT COUNT(r_cod) FROM Recetas WHERE r_codigo = p_r_codigo;
    CURSOR cpt_cod IS 
        SELECT COUNT(pt_cod) FROM Estilo WHERE pt_cod = p_estilo_pt_cod;
    CURSOR ch_cod IS 
        SELECT COUNT(h_cod) FROM Hervido WHERE h_cod = p_hervido_h_cod;
    CURSOR cw_cod IS 
        SELECT COUNT(w_cod) FROM Whirpool WHERE w_cod = p_whirpool_w_cod;
    CURSOR cf_cod IS 
        SELECT COUNT(f_cod) FROM Fermentacion WHERE f_cod = p_fermentacion_f_cod;
    CURSOR cf_dry_cod IS 
        SELECT COUNT(f_dry_cod) FROM Dry_Hopping WHERE f_dry_cod = p_dry_hopping_f_dry_cod;
    CURSOR ccar_cod IS 
        SELECT COUNT(car_cod) FROM Carbonatacion WHERE car_cod = p_carbonatacion_car_cod;
    CURSOR cm_cod IS 
        SELECT COUNT(m_cod) FROM Maceracion WHERE m_cod = p_maceracion_m_cod;
    CURSOR cr_grano IS
        SELECT COUNT(g_cod) FROM granos WHERE g_cod = p_g_cod;
    CURSOR cr_lupulo IS
        SELECT COUNT(lu_cod) FROM lupulos WHERE lu_cod = p_lu_cod;
    CURSOR cr_levadura IS
        SELECT COUNT(le_cod) FROM levaduras WHERE le_cod = p_le_cod;
     CURSOR cr_aditivo IS
        SELECT COUNT(a_cod) FROM aditivos WHERE a_cod = p_ad_cod;
    v_dummy NUMBER;
BEGIN
    -- Validaciones de nulos para parámetros generales de la receta
    IF p_r_codigo IS NULL THEN RAISE_APPLICATION_ERROR(-20001, 'El código de la receta es obligatorio.'); END IF;
    IF p_r_nombre IS NULL THEN RAISE_APPLICATION_ERROR(-20002, 'El nombre de la receta es obligatorio.'); END IF;
    IF p_r_version IS NULL THEN RAISE_APPLICATION_ERROR(-20004, 'La versión de la receta es obligatoria.'); END IF;
    IF p_r_batch_size IS NULL THEN RAISE_APPLICATION_ERROR(-20005, 'El tamaño del lote es obligatorio.'); END IF;
    IF p_r_eficiencia_size IS NULL THEN RAISE_APPLICATION_ERROR(-20006, 'La eficiencia de la receta es obligatoria.'); END IF;
    IF p_r_autor IS NULL THEN RAISE_APPLICATION_ERROR(-20007, 'El autor de la receta es obligatorio.'); END IF;
    IF p_r_fecha_c IS NULL THEN RAISE_APPLICATION_ERROR(-20008, 'La fecha de la receta es obligatoria.'); END IF;
    IF p_estilo_pt_cod IS NULL THEN RAISE_APPLICATION_ERROR(-20009, 'El estilo es obligatorio.'); END IF;
    IF p_hervido_h_cod IS NULL THEN RAISE_APPLICATION_ERROR(-20010, 'El proceso de hervido es obligatorio.'); END IF;
    IF p_whirpool_w_cod IS NULL THEN RAISE_APPLICATION_ERROR(-20011, 'El proceso de whirlpool es obligatorio.'); END IF;
    IF p_fermentacion_f_cod IS NULL THEN RAISE_APPLICATION_ERROR(-20012, 'El proceso de fermentación es obligatorio.'); END IF;
    IF p_carbonatacion_car_cod IS NULL THEN RAISE_APPLICATION_ERROR(-20014, 'El proceso de carbonatación es obligatorio.'); END IF;
    IF p_maceracion_m_cod IS NULL THEN RAISE_APPLICATION_ERROR(-20015, 'El proceso de maceración es obligatorio.'); END IF;

    -- Validaciones de atributos obligatorios para Granos
    IF p_g_cod IS NULL THEN 
        IF p_g_nombre IS NULL OR p_g_tipo IS NULL OR p_g_porcentaje IS NULL OR p_g_peso IS NULL OR p_g_potencial_extraccion IS NULL OR p_g_color IS NULL THEN
            RAISE_APPLICATION_ERROR(-20023, 'Todos los atributos del grano son obligatorios.');
        END IF;
    ELSE    
        open cr_grano;
        fetch cr_grano into v_dummy;
        if v_dummy = 0 then
            RAISE_APPLICATION_ERROR(-20015, 'Se debe enviar un grano existente en la base de datos');
        end if;
        close cr_grano;
    END IF;
    
    -- Validaciones de atributos obligatorios y restricciones (CHECK) para Lúpulos
    IF p_lu_cod is null then
        IF p_lu_variedad IS NULL OR p_lu_anio_cosecha IS NULL OR p_lu_formato IS NULL OR p_lu_porcentaje_alfa_acido IS NULL OR p_lu_cantidad IS NULL OR p_lu_proposito IS NULL THEN
            RAISE_APPLICATION_ERROR(-20024, 'Todos los atributos del lúpulo son obligatorios.');
        END IF;
        IF p_lu_proposito NOT IN ('Amargor', 'Sabor', 'Aroma') THEN
            RAISE_APPLICATION_ERROR(-20025, 'El propósito del lúpulo debe ser Amargor, Sabor o Aroma[cite: 4].');
        END IF;
    ELSE
        open cr_lupulo;
        fetch cr_lupulo into v_dummy;
        if v_dummy = 0 then
            RAISE_APPLICATION_ERROR(-20015, 'Se debe enviar un lupulo existente en la base de datos');
        end if;
        close cr_lupulo;
    END IF;
    -- Validaciones de atributos obligatorios y restricciones (CHECK) para Levaduras
    IF p_le_cod is null then
        IF p_le_cepa IS NULL OR p_le_formato_lev IS NULL OR p_le_cantidad_lev IS NULL OR p_le_tasa_inoculacion IS NULL THEN
            RAISE_APPLICATION_ERROR(-20026, 'Todos los atributos de la levadura son obligatorios.');
        END IF;
        IF p_le_formato_lev NOT IN ('Seca', 'Líquida') THEN
            RAISE_APPLICATION_ERROR(-20027, 'El formato de la levadura debe ser Seca o Líquida.');
        END IF;
    ELSE
        open cr_levadura;
        fetch cr_levadura into v_dummy;
        if v_dummy = 0 then
              RAISE_APPLICATION_ERROR(-20015, 'Se debe enviar una levadura existente en la base de datos');
        end if;
        close cr_levadura;
    END;
    -- Validaciones de atributos obligatorios para Aditivos
    IF p_ad_cod is null then
        IF p_ad_nombre_aditivo IS NULL OR p_ad_tipo_aditivo IS NULL OR p_ad_cantidad_aditivo IS NULL THEN
            RAISE_APPLICATION_ERROR(-20028, 'Todos los atributos del aditivo son obligatorios.');
        END IF;
    ELSE
        open cr_aditivo;
        fetch cr_aditivo into v_dummy;
        if v_dummy = 0 then
            RAISE_APPLICATION_ERROR(-20015, 'Se debe enviar una aditivo existente en la base de datos');
        end if;
        close cr_aditivo;
    END IF;
    -- Verificación de unicidad y existencia mediante cursores
    OPEN cr_cod; FETCH cr_cod INTO v_dummy; 
    IF v_dummy > 0 THEN RAISE_APPLICATION_ERROR(-20015, 'El código de la receta ya está registrado.'); END IF; 
    CLOSE cr_cod;

    OPEN cpt_cod; FETCH cpt_cod INTO v_dummy; 
    IF v_dummy = 0 THEN RAISE_APPLICATION_ERROR(-20016, 'El estilo especificado no existe.'); END IF; 
    CLOSE cpt_cod;

    OPEN ch_cod; FETCH ch_cod INTO v_dummy; 
    IF v_dummy = 0 THEN RAISE_APPLICATION_ERROR(-20017, 'El proceso de hervido no existe.'); END IF; 
    CLOSE ch_cod;

    OPEN cw_cod; FETCH cw_cod INTO v_dummy; 
    IF v_dummy = 0 THEN RAISE_APPLICATION_ERROR(-20018, 'El proceso de whirlpool no existe.'); END IF; 
    CLOSE cw_cod;

    OPEN cf_cod; FETCH cf_cod INTO v_dummy; 
    IF v_dummy = 0 THEN RAISE_APPLICATION_ERROR(-20019, 'El proceso de fermentación no existe.'); END IF; 
    CLOSE cf_cod;

    IF p_dry_hopping_f_dry_cod IS NOT NULL THEN
        OPEN cf_dry_cod; FETCH cf_dry_cod INTO v_dummy; 
        IF v_dummy = 0 THEN RAISE_APPLICATION_ERROR(-20020, 'El proceso de dry hopping no existe.'); END IF; 
        CLOSE cf_dry_cod;
    END IF;

    OPEN ccar_cod; FETCH ccar_cod INTO v_dummy; 
    IF v_dummy = 0 THEN RAISE_APPLICATION_ERROR(-20021, 'El proceso de carbonatación no existe.'); END IF; 
    CLOSE ccar_cod;

    OPEN cm_cod; FETCH cm_cod INTO v_dummy; 
    IF v_dummy = 0 THEN RAISE_APPLICATION_ERROR(-20022, 'El proceso de maceración no existe.'); END IF; 
    CLOSE cm_cod;

END receta_alta_vld;
