//: ----------------------------------------------------------------------------
//: Copyright (C) 2017 Verizon.  All Rights Reserved.
//:
//:   Licensed under the Apache License, Version 2.0 (the "License");
//:   you may not use this file except in compliance with the License.
//:   You may obtain a copy of the License at
//:
//:       http://www.apache.org/licenses/LICENSE-2.0
//:
//:   Unless required by applicable law or agreed to in writing, software
//:   distributed under the License is distributed on an "AS IS" BASIS,
//:   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
//:   See the License for the specific language governing permissions and
//:   limitations under the License.
//:
//: ----------------------------------------------------------------------------
import sbt._, Keys._
import com.typesafe.tools.mima.plugin.MimaPlugin

object BinCompatPlugin extends AutoPlugin {
  object autoImport {
    val binCompatVersion = settingKey[Option[String]]("version to check binary compatibility against")
  }

  import autoImport._
  import MimaPlugin.autoImport._

  override def requires = MimaPlugin && verizon.build.ScalazPlugin

  override def trigger = allRequirements

  override lazy val projectSettings = Seq(
    mimaPreviousArtifacts := binCompatVersion.value
      .fold(Set.empty[ModuleID])(v => Set(organization.value %% name.value % v))
  )
}
