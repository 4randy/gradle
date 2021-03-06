/*
 * Copyright 2016 the original author or authors.
 *
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
 */
package org.gradle.integtests.tooling.fixture

import org.gradle.integtests.fixtures.AbstractCompatibilityTestRunner
import org.gradle.integtests.fixtures.executer.GradleDistribution
import org.gradle.integtests.fixtures.versions.ReleasedVersionDistributions

class ToolingApiCompatibilitySuiteRunner extends AbstractCompatibilityTestRunner {
    static ToolingApiDistributionResolver resolver

    ToolingApiCompatibilitySuiteRunner(Class<? extends ToolingApiSpecification> target) {
        super(target)
    }

    /**
     * Tooling API tests will can run against any version back to Gradle 0.8.
     */
    @Override
    protected List<GradleDistribution> choosePreviousVersionsToTest(ReleasedVersionDistributions previousVersions) {
        return previousVersions.all
    }

    private static ToolingApiDistributionResolver getResolver() {
        if (resolver == null) {
            resolver = new ToolingApiDistributionResolver().withDefaultRepository()
        }
        return resolver
    }

    @Override
    protected void createExecutions() {
        if (implicitVersion) {
            add(new ToolingApiExecution(getResolver().resolve(current.version.version), current))
        }
        previous.each {
            if (it.toolingApiSupported) {
                add(new ToolingApiExecution(getResolver().resolve(current.version.version), it))
                add(new ToolingApiExecution(getResolver().resolve(it.version.version), current))
            }
        }
    }
}
