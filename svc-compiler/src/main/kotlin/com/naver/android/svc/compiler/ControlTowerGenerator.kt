/*
 * Copyright 2018 NAVER Corp.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */package com.naver.android.svc.compiler

import com.squareup.javapoet.ClassName
import com.squareup.javapoet.MethodSpec
import com.squareup.javapoet.TypeSpec

import javax.lang.model.element.Modifier

class ControlTowerGenerator(private val packageName: String, private val annotatedClazz: ControlTowerAnnotatedClass) {
    private val controlTowerName: String

    private val screenMethodSpec: MethodSpec
        get() = MethodSpec.methodBuilder("getScreen")
            .addModifiers(Modifier.PUBLIC)
            .addStatement("return (\$L) getBaseScreen()", annotatedClazz.screen)
            .returns(annotatedClazz.screen)
            .build()

    private val viewMethodSpec: MethodSpec
        get() = MethodSpec.methodBuilder("getViews")
            .addModifiers(Modifier.PUBLIC)
            .addStatement("return (\$L) getBaseViews()", annotatedClazz.baseView)
            .returns(annotatedClazz.baseView)
            .build()

    init {
        this.controlTowerName = annotatedClazz.clazzName
    }

    fun generate(): TypeSpec {
        val builder = TypeSpec.classBuilder(getControlTowerName())
            .addJavadoc(
                "Generated by SVC processor. (https://github.com/naver/svc). Don't change this class.\n")
            .addModifiers(Modifier.PUBLIC)
            .addModifiers(Modifier.ABSTRACT)
            .addMethod(screenMethodSpec)
            .addMethod(viewMethodSpec)
            .superclass(
                ClassName.get(
                    "com.naver.android.svc.core.controltower", "ControlTower"))
        return builder.build()
    }

    private fun getControlTowerName(): String {
        return "SVC_$controlTowerName"
    }
}
