/* ============================================================
   DATOS INICIALES
   Ejecutar una sola vez, después de Script_DATABASE.sql y ProcedimientosTx.sql.
   Barriles.Inventario_i_cod es obligatorio, pero inventario_alta necesita un barril ya cargado:
   por eso las tres etapas del inventario se crean acá, a mano.
   ============================================================ */
INSERT INTO Inventario (i_litros_totales, i_estado_etapa) VALUES (0, 'Fermentación/Maduración');
INSERT INTO Inventario (i_litros_totales, i_estado_etapa) VALUES (0, 'Para Envasar');
INSERT INTO Inventario (i_litros_totales, i_estado_etapa) VALUES (0, 'Envasados');
COMMIT;
