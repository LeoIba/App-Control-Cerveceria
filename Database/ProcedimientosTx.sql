CREATE OR REPLACE PROCEDURE registrar_movimiento(fecha IN DATE, hora IN NUMBER, lugar_origen IN VARCHAR2, lugar_destino IN VARCHAR2, responsable IN VARCHAR2, barril_cod IN NUMBER)
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

CREATE OR REPLACE PROCEDURE registrar_barril(capacidad IN NUMBER, conector IN VARCHAR2, estado IN VARCHAR2, ubicacion IN VARCHAR2, inventario_cod IN NUMBER, lote_barril_cod IN NUMBER, fecha_inicio IN DATE)
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