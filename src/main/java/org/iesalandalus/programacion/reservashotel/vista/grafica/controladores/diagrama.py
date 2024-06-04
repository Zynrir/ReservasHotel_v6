from graphic import Digraph # type: ignore

# Crear un objeto Digraph
dot = Digraph()

# Agregar nodos de las entidades
dot.node('Socio', 'Socio\n(DNI, Nombre, Fecha Nacimiento, Edad, Cuota, Puntos, Email)')
dot.node('Taller', 'Taller\n(Código Identificativo, Nombre Taller, Precio, Tipo, Nivel)')
dot.node('Monitor', 'Monitor\n(NIF, Nombre, Teléfono, Especialidad)')
dot.node('Sala', 'Sala\n(Número Sala, Aforo, [Instrumentos], [Tipo Suelo])')
dot.node('Matrícula', 'Matrícula\n(ID Matrícula, Fecha Matriculación, Descuento, DNI Socio, Código Taller)')

# Agregar relaciones
dot.edge('Socio', 'Matrícula', label='1,N')
dot.edge('Taller', 'Matrícula', label='1,N')
dot.edge('Monitor', 'Taller', label='1,N', constraint='false')
dot.edge('Monitor', 'Monitor', label='0,N\nColabora con\n0,1', constraint='false')
dot.edge('Taller', 'Sala', label='1,1\nRealiza en\n0,N', constraint='false')

# Renderizar el diagrama
dot.render('/mnt/data/diagrama_ER', format='png', cleanup=True)

# Mostrar el archivo generado
'/mnt/data/diagrama_ER.png'
