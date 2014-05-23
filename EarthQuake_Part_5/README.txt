Obtiene un listado diario de terremotos de la web, en formato XML.
Actividad de preferencias utilizando PreferenceActivity.
Almacena los valores de los terremotos en una BD SQLite. La primera ejecuci—n, accede a internet y obtiene los datos. En sucesivas conexiones, accede a la BD.
La conexi—n a Internet se procesa en un AsyncTask.