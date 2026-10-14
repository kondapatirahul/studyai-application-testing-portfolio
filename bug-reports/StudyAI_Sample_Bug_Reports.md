# StudyAI — Sample Bug Report Portfolio

**Purpose:** Demonstrate defect reporting quality for a recruiter. **All entries below are hypothetical test ideas and are NOT verified defects.** Do not claim they were observed in production or that they are reproducible until execution evidence exists.

## Defect reporting standard
- Record build/commit, environment, account type, exact steps, expected vs actual result, evidence, reproducibility, and linked test case.
- Severity reflects user/business impact; priority is provisional and should be agreed with the product team.
- Sanitize screenshots, logs, request headers, tokens, emails, and any study content before sharing.

## STUDYAI-SAMPLE-001 — AI generation loading state may persist after provider timeout

- **Status:** Sample / Not verified
- **Severity:** Major (provisional)
- **Priority:** P1 (provisional)
- **Environment:** To be recorded during validation: build/commit, browser + version, OS, backend URL, API provider configuration.
- **Preconditions:** Signed-in test user; valid source material; test environment can simulate provider timeout or upstream 5xx.

**Steps to reproduce (proposed):**
1. Sign in with a synthetic test account.
2. Open a valid study material.
3. Choose Generate Summary (repeat for quiz/flashcards if those flows share the handler).
4. Simulate provider timeout / return an upstream error.
5. Observe the UI and attempt a retry.

**Expected result:** A clear retryable error is displayed; loading indicator stops; controls return to a usable state; no duplicate generation request is created without user intent.

**Actual result:** Not observed yet. Record exact observed behavior, screenshots, console/network evidence, and request/correlation ID after reproducing.

**Notes:** Hypothesis-based sample defect. Do not present as an actual defect until reproduced in a specific build.

## STUDYAI-SAMPLE-002 — Rapid repeated click could submit duplicate generation requests

- **Status:** Sample / Not verified
- **Severity:** Major (provisional)
- **Priority:** P2 (provisional)
- **Environment:** To be recorded during validation: build/commit, browser + version, OS, backend URL.
- **Preconditions:** Signed-in user; valid study material; generation endpoint observable in network tools or server logs.

**Steps to reproduce (proposed):**
1. Open a valid material.
2. Click Generate Summary repeatedly while the first request is pending.
3. Inspect network/server logs and the generated-items list.

**Expected result:** The action is disabled/debounced or requests are idempotently handled; a single intended operation is created.

**Actual result:** Not observed yet. Count requests and persisted outputs during a controlled reproduction.

**Notes:** Potential race/idempotency issue, not a confirmed finding.

## STUDYAI-SAMPLE-003 — Malformed upload may return an unhandled error instead of validation feedback

- **Status:** Sample / Not verified
- **Severity:** Major (provisional)
- **Priority:** P1 (provisional)
- **Environment:** To be recorded during validation: build/commit, browser + version, backend logs.
- **Preconditions:** Test account; upload feature enabled; safe dummy file with unsupported extension or invalid content.

**Steps to reproduce (proposed):**
1. Open the material upload screen.
2. Select an unsupported or malformed test file.
3. Submit upload and inspect UI plus HTTP response.

**Expected result:** The file is rejected with a clear validation message and documented 4xx response; no partial material is stored; no stack trace or internal path is exposed.

**Actual result:** Not observed yet. Capture response code/body and verify storage state after execution.

**Notes:** Check documented file-type and size rules before raising as a product defect.

## STUDYAI-SAMPLE-004 — Generated quiz score may not match submitted answers

- **Status:** Sample / Not verified
- **Severity:** Major (provisional)
- **Priority:** P1 (provisional)
- **Environment:** To be recorded during validation: build/commit, browser + version, quiz fixture or deterministic expected answers.
- **Preconditions:** A deterministic quiz or controlled fixture with known correct answers and scoring rule.

**Steps to reproduce (proposed):**
1. Open the deterministic quiz.
2. Submit a mix of known correct and incorrect answers.
3. Calculate expected score using documented scoring rules.
4. Compare UI score, feedback, and analytics entry.

**Expected result:** Score and answer feedback match the scoring rule exactly; the saved attempt agrees with the displayed result.

**Actual result:** Not observed yet. Record the submitted answer set, expected score, actual score, and persisted result.

**Notes:** Do not use non-deterministic AI-generated questions alone to assert an exact answer key.

## STUDYAI-SAMPLE-005 — Study material or progress data could be exposed across user accounts

- **Status:** Sample / Not verified
- **Severity:** Critical (provisional)
- **Priority:** P1 (provisional)
- **Environment:** To be recorded during validation: two synthetic accounts, separate browser sessions, identified build/API environment.
- **Preconditions:** Two authorized test accounts, User A and User B, with distinct test materials and quiz attempts.

**Steps to reproduce (proposed):**
1. Sign in as User A and create a unique test material / quiz attempt.
2. Sign out, then sign in as User B in a clean session.
3. Inspect lists, analytics, and any relevant API responses for User A's unique test record.
4. If safe and authorized, test access controls on the specific record using User B's session.

**Expected result:** User B can access only their own authorized records; server-side authorization prevents cross-account reads/writes even if IDs are guessed.

**Actual result:** Not observed yet. Record only synthetic identifiers and sanitized evidence; stop if real user data is exposed.

**Notes:** Security test idea, not a claim of a confirmed vulnerability. Run only in an authorized test environment.
