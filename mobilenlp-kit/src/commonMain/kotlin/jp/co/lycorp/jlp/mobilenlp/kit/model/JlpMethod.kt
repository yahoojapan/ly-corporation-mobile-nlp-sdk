// SPDX-FileCopyrightText: 2025 LY Corporation
// SPDX-License-Identifier: BSD-3-Clause

package jp.co.lycorp.jlp.mobilenlp.kit.model

enum class JlpMethod(val displayName: String, val methodName: String, val path: String, val sample: String) {
    MA("日本語形態素解析", "jlp.maservice.parse", "MAService/V2/parse", "美しい水車小屋の娘"),
    JIM("かな漢字変換", "jlp.jimservice.conversion", "JIMService/V2/conversion", "きょうはよいてんきです。"),
    Furigana("ルビ振り", "jlp.furiganaservice.furigana", "FuriganaService/V2/furigana", "漢字かな交じり文にふりがなを振ること。"),
    Kousei("校正支援", "jlp.kouseiservice.kousei", "KouseiService/V2/kousei", "セキュリティー,食べれる"),
    DA("日本語係り受け解析", "jlp.daservice.parse", "DAService/V2/parse", "青空にたくさんの気球が浮かんでいた"),
    Keyphrase("キーフレーズ抽出", "jlp.keyphraseservice.extract", "KeyphraseService/V2/extract", "東京ミッドタウンから国立新美術館まで歩いて5分で着きます。"),
    NLU("自然言語理解", "jlp.nluservice.analyze", "NLUService/V2/analyze", "新宿の天気"),
    NER("固有表現抽出", "jlp.nerservice.extract", "NERService/V1/extract", "Xデータ株式会社の加藤さんは、10時に東京駅に到着しました。"),
    ;

    companion object {
        fun getSampleText(index: Int): String? = entries.getOrNull(index)?.sample
    }
}
