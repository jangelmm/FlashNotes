# FlashNotes - Documentación

**Aplición Desktop - Creada en Java Swing - Modelo Vista Controlador - Metodología Cascada (Waterfall)**

![](/Images/imagen1.png)
![](/Images/imagen2.png)

## Fase 1: Requisitos
### Requerimientos del Negocio
#### Contexto del Proyecto
- Nombre de la Organización o Programador: Jesús Ángel Martínez Mendoza
- Tipo de Organización: Independiente
- Sector: No aplica
- Producto o servicios que ofrece: Programación de aplicaciones y Sistemas
- Necesidades especificas: No aplica
- Ubicación: No aplica

#### Identificación del Problema u Oportunidad de Mejora
El problema que se identifico, es que al usar el *Blog de notas* o *Notepad* tanto en linux como en Windows se generan muchas notas innecesarias y/o basura, esto en el uso diario de estas aplicaciones; hasta el momento aplicaciones similares de notas general el mismo problema.

#### Objetivos del Negocio
Ofrecer una herramienta sencilla para notas temporales que reduzca el desorden digital en la productividad diaria de los usuarios.

#### Declaración de la Visión del Producto

**FlashNotes** es un software dirigido al publico en general, que use notas rápidas para organizar sus ideas o notas, las cuales son temporales, resuelve el problema de generación de basura y archivos innecesarios, así como dar la oportunidad de guardar la nota (en caso de requerirla), su principal ventaja, es que evita notas innecesarias, y solo temporales.


### Alcance
#### Diagrama de Contexto

```mermaid
flowchart TD
    subgraph Usuario["Usuario"]
    end

    subgraph Sistema["FlashNotes (Aplicación Desktop)"]
    end

    subgraph BD["Sistema de Archivos Local (Notas que se quieran guardar)"]
    end

    Usuario -->|Crea/edita/elimina notas| Sistema
    Sistema -->|Muestra notas temporales| Usuario
    Sistema -->|Guardar/leer notas| BD
    BD -->|Notas almacenadas| Sistema
```

#### Descripción del Alcance del Producto (Árbol de caracteristicas)

```mermaid
mindmap
  root((FlashNotes))
    Funcionalidades
      Crear nota
      Editar nota
      Eliminar nota
      Visualizar notas activas
    Gestión
      Guardado en el Sistema
      Eliminar todas las notas de forma Automatica o Manual
      Recuperar notas del Sistema Operativo
    Opciones adicionales
      Buscar en notas
      Exportar nota (TXT)
      Configuración mínima*
```
### Anexos
Entrevistas realizadas, documentos o reportes, diagramas, etc...

### Requerimientos del Usuario

**Identificación**:

- Como un **Usuario**, yo necesito **crear notas** así que de esta manera **tener texto a mi alcance**.
- Como un **Usuario**, yo necesito **que las notas se eliminen de forma manual o automatica** así que de esta manera **no genere notas innecesarias, o que fueron temporales**.
- Como un **Usuario**, yo necesito **guardar aquellas notas que considere que se deban guardar** así que de esta manera **se guarde como un `.txt` en mi computadora**.
- Como un **Usuario**, yo necesito **una Interfaz Gráfica de Usuario (GUI)** así que de esta manera **pueda tener acceso a las funcionalidades de la aplicación**.
- Como un **Usuario**, yo necesito **buscar una palabra o cadena de caracteres** así que de esta manera **pueda editar con mayor facilidad**.

**Formato**:

|Id | Nombre | Usuario | Descripción | Pruebas de Aceptación | Puntos / DI / Prioridad |
|---|--------|---------|-------------|-----------------------|-------------|
|HU1 | Creación de notas | Usuario | Crear un espacio para la nota | Verificar que el usuario tiene donde escribir y pueda realizar las notas | 1 |
|HU2 | Eliminar notas | Usuario / Aplicación | Eliminar todas las notas actuales al cerrar la aplicación o al hacer click en un botón | Verificar que al abrir la aplicación ya no haya notas, y después de hacer click | 1 |
|HU3 | Guardar notas | Usuario | Que la nota que el usuario quiera guardar se almacene en el SO con la extensión `.txt` | Verificar que el contenido del `.txt` sea el mismo que el de la aplicación al momento de guardar | 2 |
|HU4 | GUI | Usuario | Interfaz Gráfica de Usuario | Verificar que todos los botones y funcionalidades, esten funcionando como se deben | 1 |
|HU5 | Buscar Palabras | Usuario | Buscar palabra en la nota, y que mueva el puntero al inicio de la primera palabra encontrada | Ingresar texto, y verificar si esa palabra se encuentra | 3 | 

**Diagrama de Casos de Uso**
```mermaid
graph LR
    Usuario((Usuario)) 

    subgraph FlashNotes - Aplicación Desktop
        UC1(["(UC1) Crear Nota"])
        UC2(["(UC2) Eliminar Nota"])
        UC3(["(UC3) Eliminar Todas las Notas"])
        UC4(["(UC4) Guardar Nota como TXT"])
        UC5(["(UC5) Visualizar Notas Actuales"])
        UC6(["(UC6) Buscar Palabras en la Nota"])
        UC7(["(UC7) Interfaz Gráfica de Usuario"])
    end

    Usuario --> UC1
    Usuario --> UC2
    Usuario --> UC3
    Usuario --> UC4
    Usuario --> UC5
    Usuario --> UC6
    Usuario --> UC7

    %% Notas simuladas como nodos adicionales
    UC1 --> N1[/"Permite escribir texto libre como nota temporal"/]
    UC3 --> N3[/"Notas se eliminan manual o automáticamente"/]
    UC4 --> N4[/"Se guarda como archivo .txt en el sistema"/]
    UC6 --> N6[/"Busca una palabra o cadena en la nota"/]
    UC7 --> N7[/"Acceso visual a todas las funcionalidades"/]

```

### Plan y Viabilidad

#### Agrupar las historias por Prioridad

```mermaid
quadrantChart
    title Técnica basada en importancia y urgencia
    x-axis Importante --> Poco Importante
    y-axis Poco Urgente --> Urgente
    quadrant-1 No hagas esto
    quadrant-2 Alta Prioridad
    quadrant-3 Prioridad Media
    quadrant-4 Prioridad Baja

    HU1 Crear_Nota:            [0.2, 0.8]
    HU2 Eliminar_Notas:        [0.3, 0.7]
    HU3 Guardar_Nota_como_TXT: [0.3, 0.3]
    HU4 Interfaz_Grafica:      [0.15, 0.85]
    HU5 Buscar_Palabras:       [0.6, 0.4]

```


### Estimar Tiempos

#####  **Fórmula General**

$$
\text{Tiempo Total} = \left( \frac{\text{Puntos Totales}}{\text{Número de Personas}} \right) \times \text{Factor de Ajuste}
$$


* **Número de personas = 1**
* **Factor de ajuste recomendado = 1.33** (para considerar imprevistos, errores, pausas, pruebas, etc.)

Supón que estas son tus HU y sus puntos de prioridad (ahora cuanto más alto, más dificil de programar).

| ID | Historia de Usuario               | Puntos | Prioridad |
| -- | --------------------------------- | ------ | --------- |
| 1  | Crear Nota                        | 3      | Alta      |
| 2  | Eliminar Todas las Notas          | 3      | Alta      |
| 3  | Guardar Nota como TXT             | 2      | Media     |
| 4  | Interfaz Gráfica de Usuario (GUI) | 5      | Alta      |
| 5  | Buscar Palabras en la Nota        | 1      | Baja      |


**Total de puntos = 14**

Entonces:

$$
\text{Tiempo Total} = \left( \frac{14}{1} \right) \times 1.33 = 18.62 \text{ días} \approx 19 \text{ días}
$$


---
#####  **Planificación en Etapas (Estilo Cascada)**

Aunque la metodología cascada no tiene iteraciones como tal, puedes desglosarlo por fases:

| Fase                | Actividades Relacionadas                                                      | Duración Estimada |
|---------------------|-------------------------------------------------------------------------------|-------------------|
| **1. Análisis**      | - Revisar y confirmar requerimientos del usuario                             | 1 día             |
|                     | - Redactar historias de usuario y validar con ejemplos prácticos              | 1 día             |
| **2. Diseño**        | - Crear boceto de la interfaz (mockup)                                        | 1 día             |
|                     | - Definir estructura de carpetas, clases, y funciones base                    | 2 días            |
|                     | - Elegir librerías o tecnologías (por ejemplo, `tkinter`, `PyQt`, etc.)       | 1 día             |
| **3. Implementación**| - Programar funcionalidades por orden de prioridad (HU1 → HU5)               | 10 días           |
|                     | - Integrar cada funcionalidad con GUI                                         | 2 días            |
| **4. Pruebas**       | - Pruebas funcionales (crear, guardar, borrar notas)                         | 2 días            |
|                     | - Pruebas de interfaz y usabilidad                                            | 1 día             |
| **5. Documentación** | - Documentación técnica y de usuario (`README.md`, comentarios en código)     | 2 días            |
| **6. Entrega/Ajustes**| - Corrección de errores, mejoras visuales y funcionales, empaquetado final  | 2 días            |

| ** Total estimado**                                                                          | **25 días**        |


#### Estudio de Viabilidad

**Viabilidad Técnica**

| Aspecto Técnico | Disponibilidad Actual | Brechas o Necesidades | Observaciones |
|-----------------|-----------------------|-----------------------|---------------|
|Infrestructura | - | - | - | - |
|Equipo | Computadora Personal | - | - | - |
|Software | - | - | - | - |
|Capacidad del Personal | - | - | - | - |

**Viabilidad Económica**


| Recurso | Alternativa 1 (Local) | Alternativa 2 (Nube) | Costo Anual (Local) | Costo Anual (Nube) | Observaciones |
|-|-|-|-|-|-|
|Hardware|-|-|-|-|-|
|Software|-|-|-|-|-|
|Almacenamiento|Disco Local|-|-|-|-|
|Conectividad|-|-|-|-|-|


**Diagrama de Tendencias Costo - Beneficio**
No aplica

**Viabilidad Operativa**

| **Factor**                     | **Descripción**                                                                  | **Estado Actual**                     | **Necesidades o Brechas**                 | **Observaciones**                                          |
| ------------------------------ | -------------------------------------------------------------------------------- | ------------------------------------- | ----------------------------------------- | ---------------------------------------------------------- |
| **Disponibilidad de Personal** | Capacidad del personal para operar y mantener el sistema.                        | Un programador .            | Buena gestión del tiempo.                 | Es posible avanzar en tiempos razonables con organización. |
| **Procesos Actuales**          | Compatibilidad de los procesos actuales con el sistema propuesto.                | No existen procesos formales.         | Definir flujo de trabajo personal.        | Puedes aplicar Git y una metodología simple como Kanban.   |
| **Infraestructura**            | Adecuación de la infraestructura actual para soportar el sistema.                | PC personal con Linux/Windows.        | Ninguna.                                  | El proyecto no demanda muchos recursos.                    |
| **Cultura Organizacional**     | Aceptación y adaptación de los usuarios al nuevo sistema.                        | No aplica.                            | -                                         | Al ser personal, puedes validar usabilidad contigo mismo.  |
| **Soporte Técnico**            | Disponibilidad de soporte técnico para resolver problemas y mantener el sistema. | Autosoporte con búsqueda y comunidad. | Participar en foros y leer documentación. | Stack Overflow, GitHub y ChatGPT pueden ser aliados clave. |



---

## Fase 2: Diseño

### Identificación de Entidades

* `Main`
* `ControladorNotas`
* `VistaNotas`
* `GestorNotas`
* `NotaDAO`
* `Nota`

---

### Diseño Arquitectónico

#### Definición de la arquitectura

Se usará el patrón de diseño **Modelo-Vista-Controlador (MVC)** para separar responsabilidades y facilitar el mantenimiento.

```mermaid
graph TD
    Vista[Vista Java Swing]
    Controlador[Controlador]
    Modelo[Modelo Nota]

    Vista --> Controlador
    Controlador --> Modelo
    Modelo --> Vista
```

#### Selección de Tecnología

* Lenguaje: `Java 23`
* GUI: `Java Swing`
* Persistencia: Archivos `.txt` (no se usa base de datos)
* Arquitectura: `MVC`

---

### Diseño Estático

#### Diagrama de Clases UML

Note como algunas clases no poseen constructor.

```mermaid
classDiagram
    direction LR

    %% ----------------------
    %% Modelo: Clases de Datos
    %% ----------------------
    class Nota {
        - contenido: String
        + getContenido(): String
        + setContenido(String): void
    }
    note for Nota "Encapsula los datos de una nota. Es el objeto del dominio (HU1, HU3, HU5)."

    %% ----------------------
    %% Modelo: Capa de Persistencia (DAO)
    %% ----------------------
    class NotaDAO {
        + guardarNotaEnArchivo(Nota, String rutaArchivo): boolean
        + eliminarArchivoNotas(): void
    }
    note for NotaDAO "Maneja el acceso a datos. Es responsable de guardar una nota específica y de la eliminación de archivos temporales (HU2, HU3)."

    %% ----------------------
    %% Modelo: Capa de Lógica de Negocio
    %% ----------------------
    class GestorNotas {
        - List~Nota~ notas
        - NotaDAO notaDAO
        + crearNota(String): void
        + eliminarNota(int): void
        + eliminarTodas(): void
        + guardarNota(int indice, String rutaArchivo): boolean
        + buscar(String): int
        + getNotas(): List~Nota~
        + getNota(int indice): Nota
    }
    note for GestorNotas "Es el corazón de la lógica del negocio. Contiene la colección de notas y delega la persistencia a NotaDAO (HU1, HU2, HU3, HU5)."
    
    %% ----------------------
    %% Vista: Interfaz de Usuario
    %% ----------------------
    class VistaNotas {
        - notasArea: JTextArea
        - btnCrear: JButton
        - btnEliminar: JButton
        - btnEliminarTodas: JButton
        - btnGuardar: JButton
        - btnBuscar: JButton
        + mostrarNotas(List~String~): void
        + getTextoNota(): String
        + getRutaGuardado(): String
        + getIndiceNotaSeleccionada(): int
        + mostrarMensaje(String): void
        + addCrearListener(ActionListener): void
        + addEliminarListener(ActionListener): void
        + addGuardarListener(ActionListener): void
        + addBuscarListener(ActionListener): void
        + addEliminarTodasListener(ActionListener): void
    }
    note for VistaNotas "Responsable de la GUI. Permite al usuario interactuar con la aplicación y provee los datos al Controlador (HU4)."

    %% ----------------------
    %% Controlador: Orquestador
    %% ----------------------
    class ControladorNotas {
        - gestor: GestorNotas
        - vista: VistaNotas
        + ControladorNotas(GestorNotas, VistaNotas): void
        + actionPerformed(ActionEvent): void
    }
    note for ControladorNotas "Controla el flujo de la aplicación. Escucha los eventos de la Vista y coordina las acciones en el Modelo."

    %% ----------------------
    %% Punto de Entrada
    %% ----------------------
    class Main {
        + main(String[]): void
    }
    note for Main "Crea y conecta todas las instancias de las clases al inicio de la aplicación."

    %% ----------------------
    %% Relaciones
    %% ----------------------
    Main --> VistaNotas : crea instancia
    Main --> GestorNotas : crea instancia
    Main --> NotaDAO : crea instancia
    Main --> ControladorNotas : crea instancia

    GestorNotas "1" *-- "0..*" Nota : contiene
    GestorNotas --> NotaDAO : delega persistencia
    ControladorNotas --> GestorNotas : manipula estado
    ControladorNotas --> VistaNotas : actualiza GUI y escucha eventos
    NotaDAO ..> Nota : utiliza para guardar/cargar datos
```

<details>

```mermaid
classDiagram
    direction LR

    %% ----------------------
    %% Modelo: Clases de Datos
    %% ----------------------
    class Nota {
        - contenido: String
        + getContenido(): String
        + setContenido(String): void
    }
    note for Nota "Encapsula los datos de una nota. Es el objeto del dominio."

    %% ----------------------
    %% Modelo: Capa de Persistencia (DAO)
    %% ----------------------
    class NotaDAO {
        + guardarNotaEnArchivo(Nota, String rutaArchivo): boolean
        + cargarNotaDesdeArchivo(String rutaArchivo): Nota
        + guardarTodasLasNotas(List~Nota~ notas, String rutaArchivo): boolean
        + cargarTodasLasNotas(String rutaArchivo): List~Nota~
        + eliminarArchivoNotas(String rutaArchivo): void
        + cargarNotasDesdeDirectorio(String rutaDirectorio): List~Nota~
    }
    note for NotaDAO "Maneja el acceso a los datos (persistencia). Lee y escribe notas en archivos, individualmente o en lotes."

    %% ----------------------
    %% Modelo: Capa de Lógica de Negocio
    %% ----------------------
    class GestorNotas {
        - List~Nota~ notas
        - NotaDAO notaDAO
        + crearNota(String): void
        + eliminarNota(int): void
        + eliminarTodas(): void
        + guardarNota(int indice, String rutaArchivo): boolean
        + cargarNotasDesdeArchivo(String rutaArchivo): void
        + cargarNotasDesdeDirectorio(String rutaDirectorio): void
        + buscar(String): int
        + getNotas(): List~Nota~
        + getNota(int indice): Nota
    }
    note for GestorNotas "Contiene la colección de notas y delega persistencia a NotaDAO."

    %% ----------------------
    %% Vista: Interfaz de Usuario
    %% ----------------------
    class VistaNotas {
        - notasArea: JTextArea
        - btnCrear: JButton
        - btnEliminar: JButton
        - btnEliminarTodas: JButton
        - btnGuardar: JButton
        - btnBuscar: JButton
        + mostrarNotas(List~Nota~): void
        + getTextoNota(): String
        + getRutaGuardado(): String
        + getRutaCargado(): String
        + getIndiceNotaSeleccionada(): int
        + mostrarMensaje(String): void
        + addCrearListener(ActionListener): void
        + addEliminarListener(ActionListener): void
        + addGuardarListener(ActionListener): void
        + addBuscarListener(ActionListener): void
        + addEliminarTodasListener(ActionListener): void
    }
    note for VistaNotas "Presenta notas al usuario y maneja interacción sin lógica de negocio."

    %% ----------------------
    %% Controlador: Orquestador
    %% ----------------------
    class ControladorNotas {
        - gestor: GestorNotas
        - vista: VistaNotas
        + ControladorNotas(GestorNotas, VistaNotas): void
        + actionPerformed(ActionEvent): void
    }
    note for ControladorNotas "Coordina eventos de la vista y acciones en el modelo."

    %% ----------------------
    %% Punto de Entrada
    %% ----------------------
    class Main {
        + main(String[]): void
    }
    note for Main "Inicia la aplicación y conecta componentes."

    %% ----------------------
    %% Relaciones
    %% ----------------------
    Main --> VistaNotas : crea instancia
    Main --> GestorNotas : crea instancia
    Main --> NotaDAO : crea instancia
    Main --> ControladorNotas : crea instancia

    GestorNotas "1" *-- "0..*" Nota : contiene
    GestorNotas --> NotaDAO : delega persistencia
    ControladorNotas --> GestorNotas : manipula estado
    ControladorNotas --> VistaNotas : actualiza GUI y escucha eventos
    NotaDAO ..> Nota : utiliza para guardar/cargar datos
    VistaNotas ..> Nota : muestra datos
```

</details>

#### Modelo Lógico de Datos (ERD)

No aplica porque **no se usará base de datos**.

---

### Diseño Dinámico

#### HU1 – Crear una nota

Este diagrama refleja cómo se crea un nuevo objeto `Nota` a través de la capa del `GestorNotas` y se actualiza la interfaz.

```mermaid
sequenceDiagram
    actor Usuario
    participant Vista as VistaNotas
    participant Ctrl as ControladorNotas
    participant Gestor as GestorNotas
    participant Nota as Nota

    Usuario ->> Vista: Escribe texto en la interfaz
    Usuario ->> Vista: Clic en "Crear"
    Vista ->> Ctrl: notificarCrearNota(texto)
    Ctrl ->> Gestor: crearNota(texto)
    Gestor ->> Nota: new Nota(texto)
    Gestor ->> Gestor: notas.add(nuevaNota)
    Gestor -->> Ctrl: retornar lista actualizada de notas
    Ctrl ->> Vista: mostrarNotas(listadoDeStrings)
    Vista -->> Usuario: Se muestra la nueva nota en la lista
```

-----

#### HU2 – Eliminar notas (individual o todas)

Este diagrama ilustra la eliminación de una nota individual, delegando la responsabilidad de la colección de notas al `GestorNotas`.

```mermaid
sequenceDiagram
    actor Usuario
    participant Vista as VistaNotas
    participant Ctrl as ControladorNotas
    participant Gestor as GestorNotas

    Usuario ->> Vista: Selecciona nota (índice i)
    Usuario ->> Vista: Clic en "Eliminar"
    Vista ->> Ctrl: notificarEliminarNota(i)
    Ctrl ->> Gestor: eliminarNota(i)
    Gestor ->> Gestor: notas.remove(i)
    Gestor -->> Ctrl: retornar lista actualizada de notas
    Ctrl ->> Vista: mostrarNotas(listadoDeStrings)
    Vista -->> Usuario: La nota ha sido eliminada de la lista
```

Para la opción de "Eliminar todas", el flujo es similar, pero el `ControladorNotas` llamaría al método `eliminarTodas()` del `GestorNotas`.

-----

#### HU3 – Guardar notas en `.txt`

Este es el diagrama más detallado, ya que refleja el flujo completo de datos para guardar una nota específica, incluyendo la interacción con la `Vista` para obtener la ruta del archivo y la delegación a la capa de persistencia (`NotaDAO`).

```mermaid
sequenceDiagram
    actor Usuario
    participant Vista as VistaNotas
    participant Ctrl as ControladorNotas
    participant Gestor as GestorNotas
    participant DAO as NotaDAO

    Usuario ->> Vista: Selecciona una nota
    Usuario ->> Vista: Clic en "Guardar"
    Vista -->> Usuario: Diálogo para seleccionar ruta de guardado
    Usuario ->> Vista: Selecciona ruta y nombre de archivo
    Vista ->> Ctrl: notificarGuardarNota(indice, rutaArchivo)
    Ctrl ->> Gestor: guardarNota(indice, rutaArchivo)
    Gestor ->> Gestor: nota = getNota(indice)
    Gestor ->> DAO: guardarNotaEnArchivo(nota, rutaArchivo)
    DAO -->> Gestor: retornar boolean de éxito
    Gestor -->> Ctrl: retornar boolean de éxito
    Ctrl ->> Vista: mostrarMensaje(mensaje)
    Vista -->> Usuario: Muestra un mensaje de éxito o error
```

-----

#### HU4 – Interfaz Gráfica (GUI)

La existencia de la GUI (HU4) se evidencia en cada diagrama de secuencia. La `VistaNotas` está presente en todas las interacciones, mostrando la información y capturando las acciones del usuario.

-----

#### HU5 – Buscar palabra en las notas

Este diagrama muestra cómo el `Controlador` delega la lógica de búsqueda al `GestorNotas` y luego actualiza la `Vista` con los resultados.

```mermaid
sequenceDiagram
    actor Usuario
    participant Vista as VistaNotas
    participant Ctrl as ControladorNotas
    participant Gestor as GestorNotas

    Usuario ->> Vista: Escribe palabra de búsqueda
    Usuario ->> Vista: Clic en "Buscar"
    Vista ->> Ctrl: notificarBuscar(palabraClave)
    Ctrl ->> Gestor: buscar(palabraClave)
    Gestor ->> Gestor: encuentra notas que contienen la palabra
    Gestor -->> Ctrl: retornar notas coincidentes
    Ctrl ->> Vista: mostrarNotas(listaDeStringsCoincidentes)
    Vista -->> Usuario: Se muestran solo las notas con coincidencias
```

### Diseño de Interfaz de Usuario

#### Mockup básico usando PlantUML (tipo menú de aplicación):

![](/Images/Fase-2-Diseno-GUI-PlantUML.png)

<details>

```
@startsalt
skinparam BackgroundColor #2d333b
skinparam BorderThickness 1
skinparam BorderColor #4a5568
skinparam FontColor #e2e8f0

' Colores específicos para elementos dentro del Salt
skinparam salt {
    BackgroundColor #2d333b
    BorderColor #4a5568
    FontColor #e2e8f0
    LineColor #4a5568
}

{+
  {
    <size:18><b> <&pulse> <color:#90cdf4>FlashNotes - Notas Temporales</color></b></size> | . | <size:12><color:#a0aec0>2 notas activas</color></size> | <size:12><color:#fc8181><&x></color></size>
  }
  ====
  {
    [<size:12> <&plus> <color:#63b3ed>Nueva Nota</color></size>] | [<size:12> <&folder> <color:#68d391>Guardar TXT</color></size>] | [<size:12><&delete>️ <color:#fc8181>Limpiar Todo</color></size>] | . | "<color:#a0aec0><&magnifying-glass> Buscar en notas... (Ctrl+F)</color>"
  }
  ====
  {
    {SI
      <size:14><b><color:#90cdf4>Notas Activas</color></b></size>
      ----
      {[X] <size:14><b> <&file> <color:#90cdf4>Nota 1</color></b></size> [<size:10><color:#68d391>Activa</color></size>] | <size:10><color:#fc8181><&x></color></size>}
      <size:12><color:#a0aec0>Bienvenido a FlashNotes! Esta es tu aplicación...</color></size>
      <size:10><color:#a0aec0>30/07/2025 10:30:15</color></size>
      ----
      {[  ] <size:14><b> <&file> <color:#90cdf4>Nota 2</color></b></size> | <size:10><color:#fc8181><&x></color></size>}
      <size:12><color:#a0aec0>Lista de tareas para hoy: - Revisar emails...</color></size>
      <size:10><color:#a0aec0>30/07/2025 09:45:22</color></size>
      ----
      {[  ] <size:14><b> <&file> <color:#90cdf4>Nota 3</color></b></size> | <size:10><color:#fc8181><&x></color></size>}
      <size:12><color:#a0aec0>Ideas para el proyecto: Implementar búsqueda...</color></size>
      <size:10><color:#a0aec0>29/07/2025 16:20:10</color></size>
    } | {
      {
        <size:16><b><color:#90cdf4>Nota 1</color></b></size> | . | <size:12><color:#a0aec0>30/07/2025 10:30:15</color></size>
      }
      ====
      {+
        <size:14><b><color:#90cdf4>Bienvenido a FlashNotes!</color></b></size>
        .
        <size:14><color:#e2e8f0>Esta es tu aplicación de notas temporales.</color></size>
        <size:14><color:#e2e8f0>Las características principales son:</color></size>
        .
        <size:14><color:#e2e8f0>• Las notas son temporales por defecto</color></size>
        <size:14><color:#e2e8f0>• Se eliminan automáticamente al cerrar</color></size>
        <size:14><color:#e2e8f0>• Puedes guardar notas importantes como .txt</color></size>
        <size:14><color:#e2e8f0>• Búsqueda rápida en todas las notas</color></size>
        <size:14><color:#e2e8f0>• Interfaz minimalista y rápida</color></size>
        .
        <size:14><color:#e2e8f0>¡Comienza a escribir tus ideas rápidas!</color></size>
        .
        <size:14><color:#e2e8f0>Recuerda: Esta nota se eliminará automáticamente</color></size>
        <size:14><color:#e2e8f0>al cerrar FlashNotes, a menos que la guardes</color></size>
        <size:14><color:#e2e8f0>como archivo .txt</color></size>
      }
    }
  }
  ====
  {/ <size:12><color:#a0aec0>Línea: 12, Caracteres: 445</color></size> | . | <size:12><color:#ecc94b><b>⚠️ Notas temporales - Se eliminarán al cerrar</b></color></size> }
}
@endsalt
```
</details>

[Link del Diseño de la GUI](https://editor.plantuml.com/uml/jLV1Rjj64BqRy3zC4y1juLYcACcL29L8RTI50SCeOXy213cibYDvONANtIM5sAg0_47kFTMz-zHBqMFr9_c1_a9dHLAcATA8d9ImlBIM-sRcpPiP-gL9c4uC2vFjBNCbPCmqY-2GyQk9LgaCZbIeD3ntWqwdCthmYD81wejBmQya6WFjzStyR9VrUWVzylQnaac-YIxsnyxsrlRMrx3y2WsWYP4lxiQ2AmFs16284Sg4_WneqOgM4CuhJjiWOBQz1NGr1h3ShFFERjJuPRy-5H8hJy-jnxDTkvzR71fnWrwxxmz7FWntuZGqI3VSdlEU3nmUZBl-SSZCvPbAACWze9KPkC0eLfg5Q8RxouVzuVw8FXREXr-XHPySs_KBEEOmv8xlWbnYC9w8AIi158VN3evvlzrl-yETTtxbQHl5tFxwdgvIN6zM63QgC3NtGHrqHXqC_BCKfsmPJGNoBNdmuFXOXKHy2Q0VT0Pj_wUKwO1fk7XzyIZ2S8VIZWdw__xr0IeHdOee5XP31Qe6nBBuhCBUS2TY4od6rq9EzYQK6GhjC3MSK51cpBPQBVZcAD7Xxl6t1Ugp9gPcvoVPpKeCNIk6YWIohFzGpTYed0TxxT5LtC_Ul7xx0DDoAS9rVLbmQDS9gM3HyIlaPvxKKBPse5axyqhSQq8z52YdA4MWWC5z4Nm5FrBxeNg790KMXu8pBXP_IgAzHfqb0qxLGCVPTxxRTnst1st7wpXUkzU0y91NW0s8TUihz4iITIei8G42TMLaUVUxLDSUzOjNE1N6YZDY8ZIVnP4pyBezptM_F4UT_v-Zas3534s1MAjhv8dou2IAip5178qMVvjVKWpOvZIvWtif7NWkIScfHPXJMBCLIyND2lUWeVRho_FnFl_fwbwHL_IcfNExQzxLTQQc8dsaIG6qwg1pt6o6-lKrRaMUJQzaDG6RKjMCVKhdgM7J04Izk3C9ePCqj91Sn1OHZ99U8-edEF_n_HzWZMIk4bh9NQ2LmXXR-MtikGK-HrAmY8Ha4bYQg6XnQoEm6aPW8N3KckadGVzCuYSF9_bepTmNrlE4IVl-mLMae9MyIvu4VrZK5-Z5RInefP6Pg82CX0LRJm8-8UVqcDq0iI8Y5YxxuNLXvNCo-S_jaOf8uZSCh1ov5YEXIPH4oxAXv2RAUj_So2laAHBZNZRPB0zWLbac4sjvtfYbbIvADVg2GY8qUZ-cf506ihpZvYbOge9fVYccZweZxpFpzNUX_URPjhYJICoqtHTmbDSi6W-wtMetQsg7oFcWExAjw-DllzkNmBDA0w4fULvcMvQhQ5ITDDPp-dc9Ch3_E_m7)

---

### Diseño de Componentes

```mermaid
graph TD
    subgraph GUI
        VistaNotas[VistaNotas]
    end

    subgraph Lógica de Negocio
        ControladorNotas[ControladorNotas]
        GestorNotas[GestorNotas]
    end

    subgraph Persistencia
        NotaDAO[NotaDAO]
        Nota[Nota]
    end

    VistaNotas --> ControladorNotas
    ControladorNotas --> GestorNotas
    GestorNotas --> Nota
    GestorNotas --> NotaDAO
```



## Fase 3: Implementación
### Configuración del Entorno
- Entorno: Netbeans 19
- Lenguaje: Java JDK 23
- Git: git version 2.47.1.windows.2
- Gestor de paquetes: Maven
- BD: No aplica
Nota: **El código del proyecto esta alojado en una subcarpeta actual ./FlashNotes**

### Configuración de lógica y persistencia
Para esto creamos una serie de paquetes.
- modelo
- vista
- control

### 1\. Implementación de Lógica y Persistencia

#### Estructura de Paquetes

Organizar el código en los siguientes paquetes (o carpetas, en el caso de un IDE):

  - `com.mycompany.flashnotes.modelo` (o `logica`): Contendrá la clase `Nota` y `GestorNotas`.
  - `com.mycompany.flashnotes.persistencia`: Contendrá la clase `NotaDAO`.
  - `com.mycompany.flashnotes.vista`: Contendrá la clase `VistaNotas`.
  - `com.mycompany.flashnotes.control`: Contendrá la clase `ControladorNotas`.
  - `com.mycompany.flashnotes`: Contendrá la clase principal con el método `main`.

#### a) Implementación de las entidades (Modelo)

Aquí se creará la clase `Nota` con sus atributos y métodos.

**Clase `Nota`:**

[Nota.java](./FlashNotes/src/main/java/com/mycompany/flashnotes/modelo/Nota.java)

> **Nota:** La interfaz `Serializable` es importante para que el objeto `Nota` pueda ser guardado en archivos de forma eficiente.

#### b) Implementación de la capa de Persistencia (`NotaDAO`)

Aquí se implementará la clase `NotaDAO` que hemos diseñado.

**Clase `NotaDAO`:**

[NotaDAO.java](./FlashNotes/src/main/java/com/mycompany/flashnotes/persistencia/NotaDAO.java)

#### c) Implementación de la Lógica del Negocio (`GestorNotas`)

Esta clase conectará la lógica con la persistencia.

**Clase `GestorNotas`:**

[GestorNotas.java](./FlashNotes/src/main/java/com/mycompany/flashnotes/modelo/GestorNotas.java)

-----

### 2\. Codificación de la GUI de Usuario

#### a) Codificación de las interfaces Swing

Aquí crearás la clase `VistaNotas` con todos los componentes y listeners.

**Clase `VistaNotas`:**

[VistaNotas.java](./FlashNotes/src/main/java/com/mycompany/flashnotes/vista/VistaNotas.java)

Algo importante, es que para simplificar la abstracción y reducir la complejidad del código, se creo un Interfaz para la Vista.

[VistaNotasInterface.java](./FlashNotes/src/main/java/com/mycompany/flashnotes/vista/VistaNotasInterface.java)


#### b) Conexión de la UI con la lógica del negocio

Aquí se implementa la clase `ControladorNotas` para unir la `Vista` y el `Modelo`.

**Clase `ControladorNotas`:**

[ControladorNotas.java](./FlashNotes/src/main/java/com/mycompany/flashnotes/control/ControladorNotas.java)


-----

### 3\. Pruebas Unitarias y de Integración

Una vez que las clases estén implementadas, es fundamental probarlas. Te recomiendo usar un framework de pruebas como **JUnit**.

#### a) Pruebas Unitarias

  - **Para `GestorNotas`:** Verificar que los métodos `crearNota()`, `eliminarNota()`, y `buscar()` funcionen correctamente de forma aislada.
  >Estatus: C0RRECTO
  - **Para `NotaDAO`:** Probar que `guardarNotaEnArchivo()` guarde un objeto en un archivo y que `cargarNotaDesdeArchivo()` lo lea correctamente.
  >Estatus: C0RRECTO

#### b) Pruebas de Integración

  - **Flujo completo:** Puedes simular un clic en un botón del controlador y verificar que la lógica del negocio se ejecute y que la vista se actualice correctamente.
  >Estatus: C0RRECTO

## Fase 4: Pruebas

### Objetivo
Verificar que el sistema FlashNotes cumple con los requisitos funcionales y no funcionales definidos, asegurando su correcto funcionamiento, usabilidad e integración de módulos.

---

### 1. Tipos de Pruebas Aplicadas

#### 1.1 Pruebas Funcionales
Verifican que cada función del sistema actúe como se espera.

| ID     | Caso de Prueba            | Entrada                              | Resultado Esperado                        | Resultado Obtenido | Estado    |
|--------|---------------------------|--------------------------------------|-------------------------------------------|---------------------|-----------|
| CF-01  | Crear nota vacía          | Click en "Nueva Nota"                | Se muestra una nota vacía                 | ✅ Funciona          | Aprobado  |
| CF-02  | Eliminar nota seleccionada| Selección de nota y clic en "Eliminar" | Nota desaparece de la lista             | ✅ Funciona          | Aprobado  |
| CF-03  | Guardar nota              | Nota escrita y ruta válida           | Archivo `.txt` creado con el contenido    | ✅ Funciona          | Aprobado  |
| CF-04  | Interfaz Intuitiva        | Revisión de funcionamiento de botones| El sistema cumple y es intuitivo          | ✅ Funciona          | Aprovado  |
| CF-05  | Buscar Palabras           | Agregar una palabra y Enter          | Posiciona el cursor en la primera aparición            | ✅ Funciona          | Aprovado  |

---

####  1.2 Pruebas de Regresión
Verifican que nuevas funciones no rompan las anteriores.

| ID     | Cambio Reciente        | Funcionalidad Verificada        | Resultado | Estado |
|--------|------------------------|---------------------------------|-----------|--------|
| PR-01  | Añadir cambio de tema  | Crear nota, guardar nota        | ✅ OK      | Aprobado |
| PR-02  | Cambiar estructura DAO | Guardar en .txt sigue funcionando | ✅ OK    | Aprobado |
| PR-03  | Links a documentación y Sitio web | El sistema no colapsa y redirige a donde debería | ✅ OK    | Aprobado |
| PR-04  | Conteo de notas realizadas | Cuenta de forma correcta las notas en la aplicación | ✅ OK    | Aprobado |
| PR-05  | Conteo de palabras y Línea del puntero | Cuenta de forma correcta las palabras y posición actual | ✅ OK    | Aprobado |

---

#### 1.3 Pruebas de Integración
Aseguran que los módulos colaboren correctamente.

| ID     | Módulos Involucrados           | Prueba                                  | Resultado |
|--------|--------------------------------|------------------------------------------|-----------|
| PI-01  | Vista ↔ Controlador ↔ Modelo   | Crear nota y mostrarla                   | ✅ OK      |
| PI-02  | Controlador ↔ DAO              | Guardar nota desde botón de GUI          | ✅ OK      |

---

#### 1.4 Pruebas de Usabilidad
Evalúan la facilidad de uso del sistema por usuarios reales.

| ID     | Escenario Evaluado                     | Observación                             | Mejora Sugerida |
|--------|----------------------------------------|-----------------------------------------|-----------------|
| PU-01  | Usuario nuevo intenta crear y guardar  | Botones intuitivos, mensaje claro       | —               |
| PU-02  | Usuario quiere cambiar a tema oscuro   | Funcionamiento correcto                   | —               |
| PU-03  | Usuario quiere usar atajos de teclado tipicos   | Funcionamiento correcto                   | —               |

---

#### 1.5 Pruebas de Aceptación del Usuario (UAT)
Validación por el usuario final (tú o tus compañeros/profesor).

| Criterio                     | Cumplido | Observaciones               |
|-----------------------------|----------|-----------------------------|
| El sistema guarda notas     | ✅        |                             |
| El sistema elimina notas    | ✅        |                             |
| Interfaz amigable           | ✅        |                             |
| Se entiende cómo usarlo     | ✅        |                             |
| El cambio de tema funciona  | ✅        |                             |

---

### 2. Registro de Defectos

| ID     | Descripción                        | Módulo         | Estado     | Fecha Solución |
|--------|------------------------------------|----------------|------------|----------------|

---

### 3. CheckList de Requisitos Cubiertos

- [x] Crear nota
- [x] Eliminar nota
- [x] Guardar como archivo `.txt`
- [x] Mostrar lista de notas activas
- [x] Buscar palabta
- [x] Cambiar tema oscuro/claro
- [x] Acceso a documentación

---

### 4. Conclusiones de la Fase

El sistema FlashNotes ha superado exitosamente las pruebas funcionales, de integración y usabilidad.

## Fase 5: Despliegue
Esta fase se enfoca en empaquetar la aplicación FlashNotes para su distribución y en la preparación de la documentación necesaria para los usuarios finales y otros desarrolladores.

#### **Requisitos del Sistema**

* **Aplicación (`.jar`):** Requiere un Java Runtime Environment (JRE) versión 23 o superior instalado.
* **Instalador de Windows (`.exe`):** No requiere que el usuario tenga Java instalado, ya que incluye un JRE optimizado. Funciona en sistemas operativos Windows de 64 bits.
* **Instalador de Linux (`.deb`):** No requiere que el usuario tenga Java instalado. Funciona en distribuciones de Linux basadas en Debian/Ubuntu de 64 bits.

#### **Características de la Aplicación**

* **Gestión de Notas:** Creación, edición, guardado y eliminación de notas en un gestor integrado.
* **Interfaz Gráfica Intuitiva:** Interfaz de usuario simple y limpia para una experiencia de usuario fluida.
* **Soporte de Temas:** Permite alternar entre un tema claro y oscuro para adaptarse a las preferencias del usuario.
* **Búsqueda Rápida:** Funcionalidad de búsqueda que filtra las notas existentes en tiempo real.
* **Persistencia de Datos:** Las notas se guardan automáticamente, garantizando que no se pierdan los cambios.

#### **Actividades Principales**

* **Generación de Binarios:** Creación de diferentes formatos de distribución para distintos sistemas operativos.
    * **JAR ejecutable:** Empaquetado del código y sus dependencias (como FlatLaf) en un único archivo `FlashNotes-with-dependencies.jar` usando el plugin `maven-assembly-plugin`.
    * **Instalador nativo para Windows:** Creación de un archivo `FlashNotes.exe` autocontenido, utilizando `jlink` para generar un JRE optimizado y Launch4j para empaquetarlo todo.
    * **Instalador nativo para Linux:** Planificación de la generación de un paquete `FlashNotes.deb` utilizando la herramienta `jpackage` para empaquetar el JRE y la aplicación en un instalador nativo.
* **Creación de Documentación y Licencias:**
    * Redacción de una licencia **MIT** para el proyecto, que se incluirá en el repositorio y en los instaladores.
    * Preparación de un archivo `README.md` que contenga una descripción del proyecto, requisitos de instalación y una guía rápida de uso.
* **Publicación de la Aplicación:**
    * Subida del código fuente completo y los binarios generados a un repositorio de GitHub.
    * Creación de un *release* en GitHub para cada versión estable, adjuntando los instaladores correspondientes.

#### **Entregables Clave**

* **Binarios de la Aplicación:**
    * [FlashNotes.jar](/Instalador/FlashNotes.jar)
    * [FlashNotes.exe](/Instalador/FlashNotes-Install.exe) (instalador para Windows)
    * [FlashNotes.deb](/FlashNotes/flashnotes_1.0_amd64.deb) (instalador para Linux)

    Para linux la aplicación se ejecuta así:
    ```
    /opt/flashnotes/bin/FlashNotes
    ```

    Otra Opción es crear un Enlace Simbolico al PATH:
    ```
    sudo ln -s /opt/flashnotes/bin/FlashNotes /usr/local/bin/FlashNotes
    ```

    Después de esto, podrás ejecutar la aplicación simplemente con el comando `FlashNotes`.
* **Documentación:**
    * Archivo `LICENSE` con la licencia MIT del proyecto.
    * Archivo `README.md` con la información general de la aplicación.
* **Repositorio GitHub:**
    * Código fuente completo y bien estructurado.
    * Una versión publicada (release) con todos los entregables adjuntos.

## Fase 6: Mantenimiento

El mantenimiento del proyecto FlashNotes es un proceso continuo que asegura su estabilidad, funcionalidad y relevancia a largo plazo. Se invita a toda la comunidad de usuarios y desarrolladores a participar en este proceso.

#### **1. Cómo Reportar un Error (Bug)**

Si encuentras un error o un comportamiento inesperado en la aplicación, por favor, ayúdanos a corregirlo siguiendo estos pasos:

1.  **Verifica los Bugs Existentes:** Revisa la sección de [Issues](https://github.com/jangelmm/FlashNotes/issues) en GitHub para ver si el error ya ha sido reportado.
2.  **Crea un Nuevo Issue:** Si no lo encuentras, haz clic en el botón **`New Issue`** y selecciona la plantilla de **`Bug Report`**.
3.  **Proporciona la Siguiente Información:**
      * **Pasos para Reproducir:** Una lista clara de los pasos que llevan al error.
      * **Comportamiento Esperado:** Describe lo que la aplicación debería haber hecho.
      * **Comportamiento Actual:** Describe lo que la aplicación hizo de manera incorrecta.
      * **Información del Entorno:** Incluye la versión de la aplicación, tu sistema operativo (Windows, macOS, Linux) y la versión de Java que estás utilizando.

#### **2. Cómo Sugerir una Nueva Característica**

Tus ideas son valiosas para el crecimiento de la aplicación. Si tienes una sugerencia, por favor, repórtala de la siguiente manera:

1.  **Verifica las Sugerencias Existentes:** Revisa la sección de [Issues](https://github.com/jangelmm/FlashNotes/issues) para ver si alguien más ya propuso la idea.
2.  **Crea un Nuevo Issue:** Selecciona la plantilla de **`Feature Request`**.
3.  **Describe tu Idea:** Explica claramente la nueva característica y por qué sería útil para la aplicación. Si es posible, proporciona un ejemplo de cómo funcionaría.

#### **3. Cómo Contribuir al Código**

Si eres desarrollador y te gustaría contribuir, ¡eres bienvenido\!

1.  **Clona el Repositorio:**
    ```bash
    https://github.com/jangelmm/FlashNotes.git
    ```
2.  **Configura tu Entorno:**
      * Importa el proyecto en tu IDE preferido (se recomienda NetBeans o IntelliJ IDEA).
      * Asegúrate de tener instalado el JDK 23.
3.  **Realiza tus Cambios:** Implementa la corrección de errores o la nueva funcionalidad.
4.  **Ejecuta las Pruebas:** Antes de enviar tus cambios, asegúrate de que todas las funcionalidades existentes sigan operando correctamente.
5.  **Envía una Pull Request:** Sube tus cambios a tu propio *fork* del repositorio y crea una **Pull Request** para que tus cambios sean revisados e incorporados al proyecto principal.

