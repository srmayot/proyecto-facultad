# Sistema de Ventas y Mantenimiento (AppMain)

Sistema de Escritorio desarrollado en Java para la gestión comercial, control de inventario, clientes, proveedores y emisión de reportes. Proyecto final desarrollado para la carrera de Análisis de Sistemas / Programación en la UTIC.

---

## Tecnologías y Herramientas Utilizadas

* **Lenguaje:** Java (OpenJDK Temurin 26)
* **Gestor de Dependencias / Construcción:** Apache Maven (con Maven Shade Plugin para empaquetado portable)
* **Interfaz Gráfica (GUI):** Java Swing + FlatLaf (Modern Look & Feel) + AbsoluteLayout
* **Base de Datos:** PostgreSQL 16
* **Reportes y Documentación:** JasperReports & OpenPDF

---

## Funcionalidades Principales

* **Gestión de Clientes:** Alta, baja, modificación y consulta de clientes.
* **Gestión de Proveedores:** Control de proveedores activos y catálogo de suministros.
* **Gestión de Productos:** Control de stock, precios y categorías.
* **Módulo de Ventas:** Registro de transacciones comerciales de manera integrada.
* **Reportes:** Generación de informes en PDF mediante JasperReports.

---

## Estructura del Proyecto

El proyecto está organizado bajo una arquitectura en capas para separar la lógica de negocio, las vistas y el acceso a datos (DAO):

```text
AppMain/
│
├── src/main/java/py/edu/utic/lpii/
│   ├── dao/          # Clases de conexión y persistencia a PostgreSQL
│   ├── model/        # Clases de entidades (Cliente, Producto, etc.)
│   ├── view/         # Formularios e interfaces gráficas (JFrame)
│   └── AppMain.java  # Clase principal de ejecución
│
├── pom.xml           # Configuración de Maven y dependencias
└── README.md
