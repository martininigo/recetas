# Creación y Configuración REST-API

## Despliegue Endpoint de SageMaker
En esta sección se creará un Endpoint de inferencia de SageMaker en base a un modelo exportado previamente entrenado. La creación se la realizará mediante un Notebook con código Python.

 1. Entrar a la consola de Amazon SageMaker y en el menú Instancias de
    bloc de notas seleccionar la opción Crear instancia de bloc de
    notas.
 2. Colocar un nombre representativo y en el campo Tipo de instancia de
    bloc de notas seleccionar la opción ml.t2.medium. Deja el resto de
    configuración con la información que viene por defecto y al final da
    clic en Crear instancia de bloc de notas
 3. Espera hasta que el estatus cambie a ‘InService’ y abre el Notebook
    en JupyterLab 
 4. Importar el notebook deploy_end_point.ipynb que está
        en esta misma carpeta seleccionando el Kernel conda_tensorflow2_p36.
 5. Ejecutar el notebook siguiendo las instrucciones.
 6. En el menú lateral, seleccione la opción Inferencia, Puntos de
    Enlace se debería haber creado un end point nuevo, copie el nombre
    para luego configurar las variables de entorno de la función Lambda.

## Creación Función Lambda
En esta sección se creará la función lambda que recibirá una imágen, realizará el preprocesamiento correspondiente y llamará al Endpoint de inferencia de SageMaker. Luego retornará el resultado de dicha inferencia.

 1. Ingresar a la consola de AWS Lambda y seleccionar la opción Crear
    una función
 2. Dentro de la información básica, colocar el nombre y en lenguaje
    seleccionar Python 3.6
 3. Hacer click en el botón Crear función
 4. Dentro del combo Acciones, seleccionar la opción Cargar un archivo
    .zip y seleccionar el archivo lambda_function.zip que está en el
    drive.
 5. En la sección Variables de entorno hacer click en el botón Editar y
    luego en Agregar Variable de Entorno
 6. Agregar una variable con clave ENDPOINT_NAME y cómo valor colocar el
    nombre del enpoint de SageMaker obtenido en el punto 6 de la sección
    anterior.

## Asignación de permisos

En esta sección se asignan los permisos necesarios para que la función Lambda pueda invocar al Endpoint de inferencia.

 1. Ingresar a la consola administrativa IAM e ir a la opción
    Administración del acceso, Roles Seleccionar el rol asociado a la
    función lambda creada. El nombre del rol comienza con el nombre de
    la función y en el campo Entidades de Confianza dice Servicio de
    AWS: lambda
 2. Seleccionar la opción Añadir una política insertada
 3. En servicio seleccionar la opción SageMaker, en acciones seleccionar
    la opción InvokeEndpoint y en Recursos seleccionar Todos los
    recursos
 4. Hacer click en la opción Revisar la política
 5. Agregar el nombre correspondiente y hacer click en el botón Crear
    una política

## Creación Api Gateway
En esta sección se creará el Api Gateway que será el Api REST que recibirá las solicitudes HTTP con la imagen a clasificar y retornará en formato json la clasificación correspondiente y el porcentaje de certeza de dicha clasificaión. 

 1. Ingresar a la consola Api Gateway y seleccionar la opción Crear API.
    Seleccionar la opción Crear del tipo API REST.
 2. Ingresar el nombre elegido y presionar el botón Crear API.
 3. En el combo Acciones seleccionar la opción Crear	método.
 4. Seleccionar la opción POST y hacer click en el botón con tilde.
 5. En Tipo de integración seleccionar la opción Función Lambda.
 6. En el campo Función Lambda elegir la función creada en el paso
    anterior y hacer click en el botón Guardar.
 7. Hacer click en la sección Solicitud de integración y en Plantillas
    de mapeo.
 8. Hacer click en Agregar Plantilla de mapeo y colocar el Content-Type
    image/jpeg.
 9. Al hacer click en el botón con el tilde, aparecerá un popup con un
    alerta, seleccionar la opción No, usar configuración actual.
 10. En el cuerpo de la plantilla agregar el siguiente contenido:

    #set($inputRoot = $input.path('$'))
    {
        "json" : $input.json('$'),
    	"body" : "$util.escapeJavaScript($input.body).replaceAll("\\'", "'")",
    }

 11. Seleccionar la opción Configuración del menú izquierdo e ir a la
     sección Tipos de medios binarios.
 12. Hacer click en el botón Agregar tipo de medios binarios y colocar
     el valor image/jpeg.
 13. Seleccionar la opción Recursos del menú izquierdo y en el combo
     Acciones seleccionar la opción Implementar la API.
 14. En el combo Etapa de implementación colocar el nombre que se desee
     y hacer click en el botón Implementación.
 15. Con este paso el API REST está desplegada y la url para invocar
     aparece a continuación del texto Invocar URL.

## Invocación API REST 
En esta sección se indicará el formato del request para invocar el API REST.

 - Protocolo: HTTPS
- Método: POST
- Headers: Content-Type, image/jpeg
- Data-binary: archivo de imágen.

Ejemplo:

    curl --location --request POST 'https://4b4cj4hei4.execute-api.us-east-2.amazonaws.com/beta' \
    --header 'Content-Type: image/jpeg' \
    --data-binary '@/C:/sai/waste-sorting/imagenes/O4.JPG'
