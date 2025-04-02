package org.intaehwang.chapter11.replaceFunctionWithCommand;

public class Scorer {
    private int result = 0;
    private int healthLevel = 0;
    private boolean highMedicalRiskFlag = false;

    private final Candidate candidate;
    private final MedicalExam medicalExam;
    private final ScoringGuide scoringGuide;

    public Scorer(Candidate candidate, MedicalExam medicalExam, ScoringGuide scoringGuide) {
        this.candidate = candidate;
        this.medicalExam = medicalExam;
        this.scoringGuide = scoringGuide;
    }

    public int execute() {
        if (medicalExam.isSmoker()) {
            healthLevel += 10;
            highMedicalRiskFlag = true;
        }

        String certificationGrade = "regular";
        if (scoringGuide.stateWithLowCertification(candidate.isOriginState())) {
            certificationGrade = "low";
            result -= 5;
        }

        result -= Math.max(healthLevel - 5, 0);
        return result;
    }
}
