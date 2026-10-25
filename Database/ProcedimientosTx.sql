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