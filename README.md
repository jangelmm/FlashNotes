# FlashNotes - Documentación
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
El problema que se identifico, es que al usar el *Blog de notas* o *Notepad* tanto en linux como en Windows se generan muchas notas innecesarias y/o basura, esto en el uso diario de estas aplicaciones; hasta el momento aplicaciones similares de Notas general el mismo problema.

#### Objetivos del Negocio
Ofrecer una herramienta sencilla para notas temporales que reduzca el desorden digital en la productividad diaria de los usuarios.

#### Declaración de la Visión del Producto

**FlashNotes** es un software dirigido al publico en general, que use notas rápidas para organizar sus ideas o notas, las cuáles son temporales, resuelve el problema de generación de basura y archivos innecesarios, así como dar la oportunidad de guardar la nota (en caso de requerirla), su principal ventaja, es que evita notas innecesarias, y solo temporales.


### Alcance
#### Diagrama de Contexto

```mermaid
flowchart TD
    subgraph Usuario["Usuario"]
    end

    subgraph Sistema["FlashNotes (Aplicación Desktop)"]
    end

    subgraph BD["Sistema Operativo (Notas que se quieran guardar)"]
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

* `Nota`
* `InterfazUsuario`
* `ControladorNotas`
* `AlmacenamientoTemporal`

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

```mermaid
classDiagram
    class Nota {
        -contenido: String
        -fechaCreacion: LocalDateTime
        +getContenido(): String
        +setContenido(String): void
        +guardarComoTXT(): void
    }

    class InterfazUsuario {
        -areaTexto: JTextArea
        +mostrar(): void
        +mostrarNotas(List~Nota~): void
        +getContenidoNotaActual(): String
        +mostrarMensaje(String): void
    }

    class ControladorNotas {
        -notas: List~Nota~
        -interfaz: InterfazUsuario
        -almacenamiento: AlmacenamientoTemporal
        +crearNota(String): void
        +eliminarNota(int): void
        +eliminarTodasNotas(): void
        +guardarNotaComoTXT(int, String): void
        +buscarNota(String): List~Nota~
        +iniciarAplicacion(): void
    }

    class AlmacenamientoTemporal {
        +guardarNotasTemporales(List~Nota~): void
        +cargarNotasTemporales(): List~Nota~
        +limpiarNotasTemporales(): void
    }

    ControladorNotas "1" *-- "0..*" Nota : contiene
    ControladorNotas "1" -- "1" InterfazUsuario : controla
    ControladorNotas "1" -- "1" AlmacenamientoTemporal : utiliza
```

#### Modelo Lógico de Datos (ERD)

No aplica porque **no se usará base de datos**.

---

### Diseño Dinámico

Diagrama de Secuencia: HU1 - Crear una nota
```mermaid
sequenceDiagram
    actor Usuario
    participant GUI as InterfazUsuario
    participant Ctrl as ControladorNotas
    participant N as Nota
    participant Alm as AlmacenamientoTemporal

    Usuario ->> GUI: Escribir contenido en área de texto
    GUI ->> Ctrl: crearNota(contenido)
    Ctrl ->> N: new Nota(contenido)
    Ctrl ->> Ctrl: Añadir nota a la lista interna
    Ctrl ->> Alm: guardarNotasTemporales(listaNotas)
    Alm -->> Ctrl: Confirmación de guardado temporal
    Ctrl -->> GUI: Notificar actualización de notas
    GUI -->> Usuario: Mostrar nueva nota en la lista
```

Diagrama de Secuencia: HU2 - Eliminar todas las notas
```mermaid
sequenceDiagram
    actor Usuario
    participant GUI as InterfazUsuario
    participant Ctrl as ControladorNotas
    participant Alm as AlmacenamientoTemporal

    Usuario ->> GUI: Clic en "Eliminar Todas las Notas"
    GUI ->> Ctrl: eliminarTodasNotas()
    Ctrl ->> Ctrl: Limpiar lista interna de notas
    Ctrl ->> Alm: limpiarNotasTemporales()
    Alm -->> Ctrl: Confirmación de limpieza
    Ctrl -->> GUI: Notificar actualización de notas (lista vacía)
    GUI -->> Usuario: Mostrar lista de notas vacía
```

Diagrama de Secuencia: HU3 - Guardar nota como TXT

```mermaid
sequenceDiagram
    actor Usuario
    participant GUI as InterfazUsuario
    participant Ctrl as ControladorNotas
    participant N as Nota

    Usuario ->> GUI: Seleccionar nota y clic en "Guardar como TXT"
    GUI ->> GUI: Mostrar diálogo para seleccionar ruta/nombre de archivo
    Usuario ->> GUI: Seleccionar ruta y confirmar
    GUI ->> Ctrl: guardarNotaComoTXT(indiceNota, rutaArchivo)
    Ctrl ->> N: getContenido() (de la nota seleccionada)
    N ->> N: guardarComoTXT(rutaArchivo)
    N -->> Ctrl: Confirmación de guardado
    Ctrl -->> GUI: mostrarMensaje("Nota guardada exitosamente")
    GUI -->> Usuario: Mostrar mensaje de éxito
```

Diagrama de Secuencia: HU5 - Buscar Palabras en la Nota
```mermaid
sequenceDiagram
    actor Usuario
    participant GUI as InterfazUsuario
    participant Ctrl as ControladorNotas

    Usuario ->> GUI: Escribir palabra a buscar en campo de búsqueda
    Usuario ->> GUI: Clic en "Buscar Palabra"
    GUI ->> Ctrl: buscarNota(palabraClave)
    Ctrl ->> Ctrl: Iterar sobre notas y buscar coincidencias
    Ctrl -->> GUI: Notificar resultado de búsqueda (ej. índice de la primera ocurrencia)
    GUI ->> GUI: Resaltar palabra encontrada o mover puntero
    GUI -->> Usuario: Mostrar resultado de búsqueda
```

Nota sobre HU4 (Interfaz Gráfica de Usuario):
La Historia de Usuario HU4 se refiere a la existencia y funcionalidad general de la Interfaz Gráfica de Usuario (GUI). Un diagrama de secuencia describe la interacción dinámica entre objetos para una funcionalidad específica. Dado que la GUI es el medio a través del cual el usuario interactúa con todas las demás funcionalidades, y no una funcionalidad en sí misma con una secuencia de pasos única, no se le asigna un diagrama de secuencia individual. Su funcionamiento se ve implícito en todos los demás diagramas de secuencia.

---

### Diseño de Interfaz de Usuario

#### Mockup básico usando PlantUML (tipo menú de aplicación):

```plantuml
@startsalt
{
{TitleBar|FlashNotes - [Sin título]}

{MenuBar
  {+ Archivo |
    Nueva Nota (Ctrl+N) |
    Guardar como TXT (Ctrl+S) |
    ---- |
    Eliminar Nota Seleccionada |
    Salir
  }
  {+ Editar |
    Buscar (Ctrl+F)
  }
  {+ Ver |
    Barra de Herramientas |
    Barra de Estado
  }
}

{ToolBar |
  <&file> Nueva | <&save> Guardar | <&trash> Eliminar Nota | <&magnify> Buscar
}

{+------------------------------------------------------------+
| Área de Texto Principal para la Nota Actual                |
|                                                            |
| Escribe aquí tus ideas rápidas. Esta nota es temporal      |
| y se borrará al cerrar la aplicación, a menos que decidas  |
| guardarla.                                                 |
+------------------------------------------------------------+
| Notas Activas                                              |
|------------------------------------------------------------|
| [ ] Nota 1: Reunión con equipo...                    [X]   |
| [ ] Nota 2: Ideas proyecto "Aurora"...              [X]   |
|------------------------------------------------------------|
| <[Eliminar Nota Seleccionada]>                             |
+------------------------------------------------------------+
}

{StatusBar | Línea: 1, Columna: 1 | Notas activas: 2}
}
@endsalt

```

[Ver Diagrama](https://editor.plantuml.com/uml/jLJDQXin4BuR_0v33gN1iI4vcc1Y1wSjjARKImaO7oRQIIBGIclzc9fi3dsKFe1FUOHziOxs9ob9UqYM3dkGTlw--Jv9PywZzGwLx_Vk-4kaLpH3MrmeTBSBuyd145PBgS6NUn-KMT_tUnpuXNJWk7uFu6u0KojkvTP04VS0Yq1R1Cv6U7_khHei3blNXu0sHGl2P0QIowG9M3u63DdQzLp9J6gEhaejIP4GqcXCiOrOef8LYFi6oJoLF57hdWKdU5Vtk3ZyC_0xFKMXjGWfmKVYHIP9UtGld7EcAZLDYPg4n1WrQxkTlhkMYYRDy0Nl7MvvtquS_tZBh4wUpHKT6TveURsRD8ZR1eDX1nlqUmMKForLy1FwuGryjL8BcQE277airQWq5JxmlsTMn08ThCgVEs7b5G5kGha77np8b9XUM_xAPOfkL14BEiBWm-OfourjiLGLTk08hWpBm3d0BX5biX4yvaeA5B9yq4U0a94s3ZQ1U60HYpSLRce958xUCaDt4IB7Bf8ijn7JQpdiqh-QVmNhMkVZCNoZe2DVV0Crq2R8t8n6V-LbTRbk9NWiS3A6JvLykJKx4doY3gR1ic07BueyfNV6Vxhwzqkmdlnl_UghkFJ8XxUwxV2vt6l2CHmVmRbH8TDn3QtEMEiyXfFweJWZdTOlx6y0)

---

### Diseño de Componentes

```mermaid
graph TB
    subgraph GUI
        UI[InterfazUsuario]
    end

    subgraph Lógica
        CN[ControladorNotas]
    end

    subgraph Modelo
        Nota[Nota]
    end

    UI --> CN
    CN --> Nota
```



## Implementación
## Pruebas
## Despliegue
## Mantenimiento