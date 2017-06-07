## Contributing

#### GitFlow

We have chosen to follow the [gitflow](http://nvie.com/posts/a-successful-git-branching-model/) workflow (branching strategy and release management).

#### Commit Guideline

Generally, a commit message's summary should contain the JIRA Ticket number followed by a short title, i.e., <JIRA TICKET NO> <SHORT TITLE>.
Additional information should go into the commit message's description, read [How to Write a Git Commit Message](http://chris.beams.io/posts/git-commit/) for more information.

**Examples:**

```
ACT-105 Build Docker image with sbt
ACT-106 Add health check endpoint
ACT-120 Add scoverage to build
ACT-106 Add Build Info to the `/build-info` endpoint
```

**Formal format**

```
<Message> ::= <JIRA-KEY>, <NBSP>, <Summary>, <EOL>
[
  <EOL>,
  <Detail>,
  <EOL>
]

where

<Detail> ::=
[ <MoreDetail> ],
[ <Before><After> ],
[ <Justification> ],
[ <Alternatives> ]
```
