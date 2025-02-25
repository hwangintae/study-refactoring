package org.intaehwang.chapter06.introduceParameterObject;

import java.util.List;

public record Station(String name, List<Reading> readings) {
}
