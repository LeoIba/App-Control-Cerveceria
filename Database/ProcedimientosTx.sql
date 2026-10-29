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