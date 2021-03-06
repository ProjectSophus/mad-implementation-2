package io.github.ProjectSophus.mad.inference

import io.github.ProjectSophus.mad._
import information._

import Information._

class GeneralizationInferer(gen : String, spec : String) extends Inferer() {
    
    def infer(info : Information) : Seq[Information] = info match {
        
        case IsExampleOf(`spec`, obj, _) => Seq(
            IsExampleOf(gen, obj) // Should we pass along boolean
        )
        
        case IsAntiexampleOf(`gen`, obj) => Seq(
            IsAntiexampleOf(spec, obj)
        )
        
        case IsExampleOf(`gen`, obj, true) => Seq(
            IsAntiexampleOf(spec, obj)
        )
        
        case _ => Seq()
    }
    
}
