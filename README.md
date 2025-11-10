<h1>UVGenius</h1>

UVGenius es una app educativa que permite a estudiantes agendar y gestionar tutorías para cualquier curso que ellos deseen, así como proponerse como tutores ellos mismos
y comunicarse con posibles estudiantes/tutores. Para efectos de laboratorio, se simula una fuente remota de datos con fallas aleatorias controladas para ejercitar estados loading/success/error.

<h2>Enlaces relevantes</h2>
<a href="https://www.figma.com/design/NhCsUTsa9COGuMjcFLqwdu/Fase-2?node-id=1-2&p=f&t=IlGvFH6OmjQVfQEj-0">
  Figma
</a>
</br> </br>
<a href="https://youtu.be/IcPTlGkipCo">
  Video demostrativo
</a>

<h2>Diagrama</h2>

<pre>
┌───────────────────────────────────────┐
│               UI (Compose)            │
│  Login, Register, Home, Profile, etc. │
└────────────────┬──────────────────────┘
                 │  collectAsStateWithLifecycle()
                 ▼
┌───────────────────────────────────────┐
│            AppVM : ViewModel          │
│ - Maneja lógica de negocio y estados  │
│ - Coordina Room ↔ Firebase            │
│ - Expone StateFlow<HomeUiState>       │
└────────────────┬──────────────────────┘
                 │
                 ▼
┌───────────────────────────────────────┐
│            UVRepository               │
│ - Fuente de datos unificada           │
│ - Lee y escribe en Room               │
│ - Sincroniza con Firebase (online)    │
│ - Devuelve Flow<ListUsuario>          │
└────────────────┬──────────────────────┘
                 │
                 ▼
┌──────────────────────────┐     ┌─────────────────────────────┐
│        Room (Local)      │     │  Firebase Realtime Database │
│ - UVDatabase, Dao,       │◀──▶│ - Usuarios, Tutorías, etc.  │
│   Entities, Converters   │     │ - Fuente remota de verdad   │
└──────────────────────────┘     └─────────────────────────────┘
</pre>

Estados de la UI (mutuamente excluyentes):
Cargando -> Muestra un spinner centrado sobre las tutorías actuales
Success -> Muestra la lista de tutorías


<h2>Decisiones de Diseño</h2>
Los puntos que se desarrollaron son:
<ul>
  <li>Se implementó una arquitectura <code>offline-first</code> en la cual Room actúa como caché local y Firebase como fuente remota de datos.</li>
  <li>La clase <code>UVRepository</code> centraliza toda la comunicación entre la base de datos local y Firebase, manejando sincronización y conflictos de forma controlada.</li>
  <li>El <code>ViewModel (AppVM)</code> gestiona los estados globales de la app mediante <code>StateFlow</code>, coordinando actualizaciones en la UI sin exponer la lógica de datos.</li>
  <li>La base de datos local usa <code>Entities</code> y <code>Converters</code> personalizados para manejar listas y objetos complejos (por ejemplo, <code>tutorias</code>).</li>
  <li>Se utilizan <code>Mappers</code> para transformar datos entre modelos locales y modelos de dominio, manteniendo independencia entre capas.</li>
  <li>La UI declarativa (Compose) observa los estados mediante <code>collectAsStateWithLifecycle()</code>, asegurando coherencia visual ante rotaciones o suspensiones.</li>
  <li>Las operaciones de inserción y actualización se realizan mediante <code>upsert</code>, garantizando consistencia entre Room y Firebase.</li>
  <li>El modo offline permite seguir usando la app incluso sin conexión; al reconectarse, los datos se sincronizan automáticamente con Firebase.</li>
  <li>La modularidad actual permite escalar fácilmente a Firestore o integrar autenticación con Firebase Auth sin modificar la estructura base.</li>
</ul>
