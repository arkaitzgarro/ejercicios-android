Obtiene un listado diario de terremotos de la web, en formato XML.
Actividad de preferencias utilizando PreferenceActivity.
Almacena los valores de los terremotos en una BD SQLite. La primera ejecución, accede a internet y obtiene los datos. En sucesivas conexiones, accede a la BD.
La conexión a Internet se procesa en un AsyncTask.
Acceso a datos a través de Content Provider