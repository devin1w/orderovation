package com.orderovation.organization.domain.model.project.team;

/**
 * @author devin
 */

public enum TeamType {
    // 需求团队
    PRODUCTION(1, "需求团队"),
    // 开发团队
    DEVELOPER(2, "开发团队"),
    // 测试团队
    TESTER(3, "测试团队"),
    // 运维团队
    MAINTAINER(4, "运维团队"),
    // 运营团队
    OPERATOR(5, "运营团队");

    private Integer code;
    private String name;

    TeamType(Integer code, String name) {
        this.code = code;
        this.name = name;
    }

    public static TeamType valueOfCode(String teamType) {
        int code = Integer.parseInt(teamType);
        switch (code) {
            case 1:
                return PRODUCTION;
            case 2:
                return DEVELOPER;
            case 3:
                return TESTER;
            case 4:
                return MAINTAINER;
            case 5:
                return OPERATOR;
            default:
                ;
        }
        return null;
    }

    public Integer getCode() {
        return code;
    }

    public String getName() {
        return name;
    }
}
