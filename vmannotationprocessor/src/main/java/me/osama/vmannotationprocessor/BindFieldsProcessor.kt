package me.osama.vmannotationprocessor

import com.google.auto.service.AutoService
import com.squareup.kotlinpoet.*
import me.osama.vmannotation.BindField
import java.io.File
import javax.annotation.processing.*
import javax.lang.model.SourceVersion
import javax.lang.model.element.ElementKind
import javax.lang.model.element.ExecutableElement
import javax.lang.model.element.TypeElement
import javax.lang.model.element.VariableElement
import javax.lang.model.util.ElementFilter

@AutoService(Processor::class)
@SupportedSourceVersion(SourceVersion.RELEASE_8)
@SupportedOptions(BindFieldsProcessor.KAPT_KOTLIN_GENERATED_OPTION_NAME)
class BindFieldsProcessor : AbstractProcessor() {

    companion object {
        const val KAPT_KOTLIN_GENERATED_OPTION_NAME = "kapt.kotlin.generated"
    }

    override fun process(annotations: MutableSet<out TypeElement>?, roundEnv: RoundEnvironment?): Boolean {
        roundEnv?.getElementsAnnotatedWith(BindField::class.java)?.forEach { methodElement ->
            if (methodElement.kind != ElementKind.METHOD) {
                processingEnv.messager.errormessage { "Can only be applied to functions,  element: $methodElement " }
                return false
            }

            (methodElement as ExecutableElement).parameters.forEach { variableElement ->
                generateNewMethod(methodElement, variableElement, processingEnv.elementUtils.getPackageOf(methodElement).toString())
            }
        }

        return false
    }

    private fun generateNewMethod(method: ExecutableElement, variable: VariableElement, packageOfMethod: String) {
        val kaptKotlinGeneratedDir = processingEnv.options[KAPT_KOTLIN_GENERATED_OPTION_NAME]
                ?: run {
                    processingEnv.messager.errormessage { "Can't find the target directory for generated Kotlin files." }
                }

        val variableAsElement = processingEnv.typeUtils?.asElement(variable.asType())
        val fieldsInArgument = ElementFilter.fieldsIn(variableAsElement?.enclosedElements)
        val annotationArgs = method.getAnnotation(BindField::class.java).viewIds

        val funcBuilder = FunSpec.builder("bindFields")
                .addModifiers(KModifier.PUBLIC)
                .addParameter(variable.simpleName.toString(), variableAsElement?.asType()?.asTypeName()!!)
                .addParameter(method.getAnnotation(BindField::class.java).viewName, ClassName("android.view", "View"))

        annotationArgs.forEachIndexed { index, viewId ->
            funcBuilder.addStatement(
                    "%L.findViewById<%T>(R.id.%L).text = %L.%L",
                    method.getAnnotation(BindField::class.java).viewName,
                    ClassName("android.widget", "TextView"),
                    viewId,
                    variable.simpleName,
                    fieldsInArgument[index].simpleName
            )
        }
        val file = File(kaptKotlinGeneratedDir as String)
        file.mkdir()
        FileSpec.builder(packageOfMethod, "BindFieldsGenerated").addFunction(funcBuilder.build()).build().writeTo(file)
    }

    override fun getSupportedAnnotationTypes(): MutableSet<String> {
        return mutableSetOf(BindField::class.java.canonicalName)
    }
}