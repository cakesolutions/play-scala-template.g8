## 1. Formatting style

Scala's syntax is so rich that formatting it manually is cumbersome.
[scalafmt](http://scalameta.org/scalafmt/) is very good at enforcing rules,
whilst being highly configurable. It is not tied to a specific IDE and and can
be used from the command-line (therefore in sbt) or using IDE plugins. Having a
company-wide configuration for formatting is wise, as it retains consistency
between projects.

### 1.1 Formatting choices :

#### Max column

```
maxColumn = 80
```

This asks scalafmt to do its best to format the code in a way that does not
go above 80 characters. 80 is a good number which allows for screen splitting
without having to scroll horizontally in many situations. Especially useful
when having to review pull requests in a side-by-side view.

#### Default style

```
style = defaultWithAlign
```

This packages several alignment parameters : mixed traits, arrows in
for-comprehensions, `=` in assignments ...


#### Dangling parenthesis

```
danglingParentheses = true
```

Line will break before the last parenthesis of a multi-line function call or
definition:

```scala

def function(
  param1 : Int,
  param2 : String,
  param3 : Boolean
)

```

#### Indentation

```
indentOperator      = spray
align.openParenCallSite = false
align.openParenDefnSite = false
continuationIndent.callSite = 2
continuationIndent.defnSite = 2
```

The Spray indent operator will allow to aling block brackets in certain pieces
of linking blocks of code via infix operators. This is heavily used by the
Spray framework (and Akka HTTP)

An indent of 2 spaces is enforced when defining or calling a function that would
require multiple lines.


#### Breaking lines between function calls

```
optIn.breakChainOnFirstMethodDot = true
```

This lets the developer break lines before dotted function calls, in order
to have a nice functional flow :

```scala
val value : Int = someObject
    .function1(a1)
    .function2(a2)
    .function3(a3)
```

#### Making imports nicer

```
spaces {
  inImportCurlyBraces = true
}
```

This adds some between brackets and namespaces when grouping imports together.

#### Avoiding scalafmt being called on irrelevant files

```
project.git = true
```

This will constrain scalafmt to take only the files tracked by git into account,
therefore leaving the generated code aside.


### 1.2 Overriding behavior

Though the formatting rules are incredibly useful in most cases, there are
particular cases where the developer would do a better formatting job. The rules
can be exceptionally disabled like this :

```scala
  // format: off
  // formatter disabled to write matrix.
  val identity = Array(1, 0, 0,
                       0, 1, 0,
                       0, 0, 1)
  // format: on
```

If you are to do this, please explain your reasons in a comment. If you think
the rule should be automated, raise it as a ticket or seek support on gitter or
github.
