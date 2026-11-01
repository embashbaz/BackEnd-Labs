# Git Workflow

## Branching

- `main` — stable
- `dev` — integration
- `feature/<topic>` — one feature per branch

## Common Commands

    git checkout dev
    git pull origin dev
    git checkout -b feature/<name>

    git add <file>
    git commit -m "type(scope): message"
    git push -u origin feature/<name>

## Commit Style

`type(scope): message`

Types: `feat`, `fix`, `refactor`, `test`, `docs`, `ci`, `chore`.

## Cherry-pick

    git log --oneline feature/testing     # find the hash
    git checkout dev
    git cherry-pick <hash>
    git push origin dev

## Merge

    git checkout dev
    git merge --no-ff feature/<name> -m "merge: feature/<name> into dev"
    git push origin dev

## CI

`.github/workflows/clean-code-ci.yml` runs on every push:

1. Set up JDK 21
2. `./gradlew build -x test`
3. `./gradlew test`
4. Upload JAR on success

Working directory is quoted because the path contains spaces and `&`:

    working-directory: 'Java/Clean Code, Testing & Git/bankMngmt'

Ubuntu runners are case-sensitive: `Java/` does not equal `java/`.

## History Check

    git log --oneline --graph --decorate --all