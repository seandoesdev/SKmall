package com.sk.skmall.domain.base;

import jakarta.validation.GroupSequence;
import jakarta.validation.groups.Default;

import com.sk.skmall.domain.base.ValidationGroups.NotEmptyGroup;
import com.sk.skmall.domain.base.ValidationGroups.PatternCheckGroup;

/**
 * 유효성 검사 순서
 * Default.class -> NotEmptyGroup.class -> PatternCheckGroup.class
 */
@GroupSequence({Default.class, NotEmptyGroup.class, PatternCheckGroup.class, })
public interface ValidationSequence {
}
