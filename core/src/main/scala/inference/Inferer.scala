package io.github.ProjectSophus.mad.inference

import io.github.ProjectSophus.mad.information._

abstract class Inferer() {
    def infer(info : Information) : Seq[Information]
}
