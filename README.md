<h1>UVGenius</h1>

UVGenius es una app educativa que permite a estudiantes agendar y gestionar tutorías para cualquier curso que ellos deseen, así como proponerse como tutores ellos mismos
y comunicarse con posibles estudiantes/tutores. Para efectos de laboratorio, se simula una fuente remota de datos con fallas aleatorias controladas para ejercitar estados loading/success/error.

<h2>Enlaces relevantes</h2>
<a href="https://www.figma.com/design/NhCsUTsa9COGuMjcFLqwdu/Fase-2?node-id=1-2&p=f&t=IlGvFH6OmjQVfQEj-0">
  Figma
</a>
</br> </br>
<a href="https://youtu.be/TcM86jbUxTw">
  Video demostrativo
</a>

<h2>Diagrama</h2>

<pre>
┌─────────────────┐
│   UI (Compose)  │
└───────┬─────────┘
        │ collectAsStateWithLifecycle()
        ▼
┌──────────────────────────┐        Flow
│    AppVM : ViewModel     │ ────────────────────────────────┐
│  - homeUiState: StateFlow│                                 │
│  - usuarioLogeado        │                                 │
└──────────┬───────────────┘                                 │
           │ cargarTutorias()                                │
           ▼                                                 │
┌─────────────────────────┐  delay + Random success/failure  │
│     FakeRepository      │──────────────────────────────────┘
│ getTutorias(): Flow     │
└─────────────────────────┘
</pre>

Estados de la UI (mutuamente excluyentes):
Cargando -> Muestra un spinner centrado sobre las tutorías actuales
Success -> Muestra la lista de tutorías


<h2>Decisiones de Diseño</h2>
Los puntos que se quisieron desarrollar en la aplicación hasta el momento son:
<ul>
  <li>Se usa una arquitectura que maneja estados (<code>HomeUiState</code>) inmutables que dentro de ellos tiene <code>isLoading</code>, <code>tutorias</code> y <code>error</code>.</li>
  <li>El ViewModel (<code>AppVM</code>) maneja completamente el <code>homeUiState</code> y <code>StateFlow&lt;HomeUiState&gt;</code>.</li>
  <li>La UI no maneja ni procesa datos; todo el procesamiento de lógica y datos ocurre en el ViewModel (no hay side effects).</li>
  <li><code>FakeRepository</code> simula latencia y error (con un 50% de probabilidad de fallo).</li>
  <li>Cuando se recibe un success, se actualiza <code>homeUiState</code> y se sincroniza <code>usuarioLogeado.tutorias</code> para consistencia visual.</li>
</ul>
