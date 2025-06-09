package com.example.cosmeticosmos

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

//esto para poder tener inyecciones de dependencias con Hilt
// para mi, esto va en otra parte del proyecto
@HiltAndroidApp
class MyApp : Application()