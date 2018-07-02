
Gradle 4.9 includes Kotlin DSL 0.18.4, bringing faster and leaner configuration time, improvements to the IntelliJ IDEA and Android Studio user experience as well as a set of bug fixes.
See details in the [Kotlin DSL v0.18.x release notes](https://github.com/gradle/kotlin-dsl/releases/tag/v0.18.4).

## New and noteworthy

Here are the new features introduced in this Gradle release.

### Command line args supported by JavaExec

Since Gradle 4.9, the command line arguments can be passed to `JavaExec` with `--args`. For example, if you want to launch the application with command line arguments `foo --bar`,
you don't need to hardcode it into the build script - you can just run `gradle run --args 'foo --bar'` (see [application plugin](userguide/application_plugin.html) for more information).

### Improved publishing documentation

This release of Gradle includes a [new Publishing Overview chapter](/userguide/publishing_overview.html) in the user manual and updates throughout the documentation regarding publishing artifacts using Maven and Ivy.  

### Improved dependency insight report

The [dependency insight report](/userguide/inspecting_dependencies.html#sec:identifying_reason_dependency_selection) is the distant ancestor of [build scans](https://scans.gradle.com) and helps you diagnose dependency management problems locally.
This release of Gradle implements several improvements:

- using `failOnVersionConflict()` no longer fails the dependency insight report in case of conflict
- all participants of conflict resolution are shown
- modules which were rejected by a rule are displayed
- modules which didn't match the version selector but were considered in selection are shown
- all custom reasons for a component selection are shown
- ability to restrict the report to one path to each dependency, for readability
- resolution failures are displayed in the report

### Continuing development of Native ecosystem

[The Gradle Native project continues](https://github.com/gradle/gradle-native/blob/master/docs/RELEASE-NOTES.md#changes-included-in-gradle-49) to improve and evolve the native ecosystem support for Gradle.

### Faster clean checkout builds

Gradle now stores more state in the Gradle user home instead of the project directory. Clean checkout builds on CI should now be faster as long as the user home is preserved.

### Support for projects with auxiliary publications

Projects which add additional publications with the maven-publish or ivy-publish plugins (e.g. publishing test fixtures along with the main code) can now be depended on by other projects in the same build.
This was a common blocker for users adopting those plugins.

### Java and Groovy compiler no longer leak file descriptors

The Java and Groovy compilers both used to leak file descriptors when run in-process (which is the default).
This could lead to "cannot delete file" exceptions on Windows and "too many open file descriptors" on Unix.
These leaks have been fixed.  If you had switched to forking mode because of this problem, it is now safe to switch back to in-process compilation.

<!--
IMPORTANT: if this is a patch release, ensure that a prominent link is included in the foreword to all releases of the same minor stream.
Add-->

<!--
### Example new and noteworthy
-->

## Promoted features

Promoted features are features that were incubating in previous versions of Gradle but are now supported and subject to backwards compatibility.
See the User guide section on the “[Feature Lifecycle](userguide/feature_lifecycle.html)” for more information.

The following are the features that have been promoted in this Gradle release.

### Dependency insight report

The dependency insight report is now considered stable.

### Tooling API types and methods

Many types and methods that were previously marked `@Incubating` are now considered stable. 

## Fixed issues

## Deprecations

Features that have become superseded or irrelevant due to the natural evolution of Gradle become *deprecated*, and scheduled to be removed
in the next major Gradle version (Gradle 5.0). See the User guide section on the “[Feature Lifecycle](userguide/feature_lifecycle.html)” for more information.

The following are the newly deprecated items in this Gradle release. If you have concerns about a deprecation, please raise it via the [Gradle Forums](https://discuss.gradle.org).

<!--
### Example deprecation
-->

## Potential breaking changes

### `EclipseProject` tasks defined for `gradle eclipse` may now run in Buildship

The [EclipseClasspath](dsl/org.gradle.plugins.ide.eclipse.model.EclipseClasspath.html) and [EclipseProject](dsl/org.gradle.plugins.ide.eclipse.model.EclipseProject.html) tasks both accept `beforeMerged` and `whenMerged` closures, for advanced Eclipse-specific customisation.

Previous versions of Gradle did not execute the closures defined in `EclipseProject` when invoked from Buildship (only those in `EclipseClasspath`). Now Gradle executes them both, similarly to when invoked from the command-line.

This leads to a potential change of behavior in this scenario:
 - These closures were defined for use with `gradle eclipse`
 - The gradle project was later imported into Eclipse, but these definitions were not removed.

The code in these closures will now become active in the `Gradle -> Refresh Gradle Project` action.

<!--
### Example breaking change
-->

### Using Groovy GPath with `tasks.withType()`

In previous versions of Gradle, it was sometimes possible to use a [GPath](http://docs.groovy-lang.org/latest/html/documentation/#gpath_expressions) expression with a project's task collection to build a list of a single property for all tasks.

For instance, `tasks.withType(SomeTask).name` would create a list of `String`s containing all of the names of tasks of type `SomeTask`. This was only possible with the method [`TaskCollection.withType(Class)`](javadoc/org/gradle/api/tasks/TaskCollection.html#withType-java.lang.Class-).

Plugins or build scripts attempting to do this will now get a runtime exception.  The easiest fix is to explicitly use the [spread operator](http://docs.groovy-lang.org/latest/html/documentation/#_spread_operator).

## External contributions

We would like to thank the following community members for making contributions to this release of Gradle.

- [Luke Usherwood](https://github.com/lukeu) Fix `ClassCastException` when generating Eclipse files for Gradle (gradle/gradle#5278)
- [Luke Usherwood](https://github.com/lukeu) Make Buildship's "Refresh Gradle Project" action honour more of the `EclipseProject` task (eclipse/buildship#694)
- [Theodore Ni](https://github.com/tjni) Reduce string allocations when working with paths. (gradle/gradle#5543)
- [Theodore Ni](https://github.com/tjni) Suppress redundant warning message (gradle/gradle#5544)
- [Lars Grefer](https://github.com/larsgrefer) Remove dependencies between `javadoc` tasks of dependent Java projects (gradle/gradle#5221)
- [Aaron Hill](https://github.com/Aaron1011) Continue executing tests if irreplaceable security manager is installed (gradle/gradle#5324)
- [Jonathan Leitschuh](https://github.com/JLLeitschuh) Throw `UnknownDomainObjectException` instead of `NullPointerException` when extension isn't found (gradle/gradle#5547)
- [thc202](https://github.com/thc202) Fix typo in TestKit chapter (gradle/gradle#5691)

We love getting contributions from the Gradle community. For information on contributing, please see [gradle.org/contribute](https://gradle.org/contribute).

## Known issues

Known issues are problems that were discovered post release that are directly related to changes made in this release.
