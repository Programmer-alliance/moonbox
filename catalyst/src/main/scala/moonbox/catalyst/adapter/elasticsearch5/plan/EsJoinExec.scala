/*-
 * <<
 * Moonbox
 * ==
 * Copyright (C) 2016 - 2019 EDP
 * ==
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * >>
 */

package moonbox.catalyst.adapter.elasticsearch5.plan

import moonbox.catalyst.core.CatalystContext
import moonbox.catalyst.core.plan.{CatalystPlan, JoinExec}
import org.apache.spark.sql.catalyst.expressions.Expression
import org.apache.spark.sql.catalyst.plans._

import scala.collection.mutable

class EsJoinExec(leftKeys: Seq[Expression],
                 rightKeys: Seq[Expression],
                 joinType: JoinType,
                 condition: Option[Expression],
                 left: CatalystPlan,
                 right: CatalystPlan) extends JoinExec(leftKeys, rightKeys, joinType, condition, left, right) {

    override def translate(context: CatalystContext): Seq[String] = {
        val seq1: Seq[String] = left.translate(context)
        val seq2: Seq[String] = right.translate(context)
        seq2 ++ seq1 ++ Seq.empty[String]
    }
}

object EsJoinExec {
    def apply(leftKeys: Seq[Expression],
              rightKeys: Seq[Expression],
              joinType: JoinType,
              condition: Option[Expression],
              left: CatalystPlan,
              right: CatalystPlan): EsJoinExec = {
        new EsJoinExec(leftKeys, rightKeys, joinType, condition, left, right)
    }
}
