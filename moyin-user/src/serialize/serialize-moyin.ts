import { DomEditor, SlateEditor, SlateElement, SlateNode, SlateText } from '@wangeditor/editor'
import type { IDomEditor } from '@wangeditor/editor'
import type { Break } from '@/core/break'
import type { Phoneme } from '@/core/phoneme'
import type { Prosody } from '@/core/prosody'
import type { SayAs } from '@/core/say-as'
import type { Sub } from '@/core/sub'
import type { Speak } from '@/core/speak'
import type { CustomManagement, SSMLElementType } from '@/core/custom-types'
import { useEditorStore, useSSMLStore } from '@/stores'
import type { MoyinW } from '@/core/moyin-w'
import { convertSoftlyTo5 } from '@/utils'

/**
 *
 * @param pinyin
 * @returns
 */
function formatPinyin(pinyin: string) {
  return convertSoftlyTo5(pinyin)
}

/**
 * 序列化 break 标签
 * @param node
 * @returns
 */
function serializeBreak(node: Break) {
  if (node.time) {
    return `<break time="${node.time}"/>`
  } else if (node.strength) {
    switch (node.strength) {
      case 'x-weak':
        return `<break time="200ms" />`
      case 'weak':
        return `<break time="300ms" />`
      case 'medium':
        return `<break time="500ms" />`
      case 'strong':
        return `<break time="700ms" />`
      case 'x-strong':
        return `<break time="1000ms" />`
      default:
        return `<break time="200ms" />`
    }
  }
  return `<break />`
}

/**
 * 多音字
 * 中文 <p phoneme="du1">都</p>
 * 英文 <p phoneme="hʌ`ləu">hello</p>
 *
 * @param node
 * @param children
 * @returns
 */
function serializePhoneme(node: Phoneme, children: string) {
  if (!node.ph) return children
  return ` <p phoneme="${formatPinyin(node.ph)}">${children}</p>`
}

/**
 * 局部变速
 * @param node
 * @param children
 * @returns
 *
 * 更新
 * 1. 语速从
 *  \<prosody rate="-50%">${children}</prosody>
 * 变为
 *  \<option speed="0.5">${children}</option>
 */
function serializeProsody(node: Prosody, children: string) {
  if (!node.contour && !node.pitch && !node.range && !node.rate && !node.volume) return children
  const contour = node.contour ? ` contour="${node.contour}"` : ''
  const pitch = node.pitch ? ` pitch="${node.pitch}"` : ''
  const range = node.range ? ` range="${node.range}"` : ''
  const volume = node.volume ? ` volume="${node.volume}"` : ''
  // const rate = node.rate ? ` rate="${node.rate}"` : ''
  // node.rate = node.rate ? node.rate : node.rate
  const speed = node.rate ? ` speed="${node.rate}"` : ''
  // return `<prosody${contour}${pitch}${range}${volume}${rate}>${children}</prosody>`
  // 更新
  return `<option${contour}${pitch}${range}${volume}${speed}>${children}</option>`
}

/**
 * 序列化 option 标签
 * 变速和变调支持
 * 0.5 < speed < 2.0
 * -10 <= pitch <= 10
 * @param node
 * @param children
 */
function serializeOption(node, children: string) {
  const pitch = node.pitch ? ` pitch="${node.pitch}"` : ''
  const speed = node.speed ? ` speed="${node.speed}"` : ''
  return `<option${speed}${pitch}>${children}</option>`
}

/**
 * 序列化 sayas 标签
 * @param node
 * @param children
 * @returns
 */
function serializeSayAs(node: SayAs, children: string) {
  if (!node.interpretAs) return children
  let interpretAs
  // 整数读法
  if (node.interpretAs === 'cardinal') {
    interpretAs = 'value'
  }
  // 数字串读
  else if (node.interpretAs === 'characters') {
    interpretAs = 'digits'
  }
  // 英文中的中文整数读法
  else if (node.interpretAs === 'mandarin_value') {
    interpretAs = 'mandarin_value'
  }
  // 英文中的中文数字读法
  else if (node.interpretAs === 'mandarin_digits') {
    interpretAs = 'mandarin_digits'
  }
  // 英文中的中文日期读法
  else if (node.interpretAs === 'mandarin_date') {
    interpretAs = 'mandarin_date'
  }
  // 英文中的中文电话号码读法
  else if (node.interpretAs === 'mandarin_telephone') {
    interpretAs = 'mandarin_telephone'
  } else {
    node.interpretAs
  }
  return `<say-as interpret-as="${interpretAs}">${children}</say-as>`
}

/**
 * 序列化 w 标签
 *  指定分词不拆分
 *  并可指定词发音，读音空格隔开
 * @param node
 * @param children
 * @returns
 */
function serializeMoyinW(node: MoyinW, children: string) {
  const phoneme = node.phoneme ? ` phoneme="${node.phoneme}"` : ''
  return `<w${phoneme}>${children}</w>`
}

/**
 *
 * @param node
 * @param children
 * @returns
 */
function serializeSub(node: Sub, children: string) {
  // return `<sub alias=${node.alias}>${children}</sub>`
  return `${node.alias}`
}

/**
 *
 * @param node
 * @param children
 * @returns
 */
function serializeSpeak(node: Speak, children: string) {
  return `<speak version="${node.version}" xml:lang="${node['xml:lang']}" xmlns="${node.xmlns}">${children}</speak>`
}

/**
 *
 * @param node 序列化节点
 * @returns
 */
function serializeNode(node: SlateNode): string {
  if (SlateText.isText(node)) {
    return node.text.trim()
  } else if (SlateElement.isElement(node)) {
    const children = node.children.map((n) => serializeNode(n)).join('')
    const type = DomEditor.getNodeType(node) as SSMLElementType
    switch (type) {
      case 'ssml-speak':
        return serializeSpeak(node as Speak, children)
      case 'ssml-break':
        return serializeBreak(node as Break)
      case 'ssml-phoneme':
        return serializePhoneme(node as Phoneme, children)
      case 'ssml-prosody':
        //@ts-ignore
        if (node.remark == '连读') {
          return serializeMoyinW({ ...(node as Prosody), type: 'moyin-w' }, children)
        }
        return serializeProsody(node as Prosody, children)
      case 'ssml-say-as':
        return serializeSayAs(node as SayAs, children)
      case 'ssml-sub':
        return serializeSub(node as Sub, children)
      case 'moyin-w':
        return serializeMoyinW(node as MoyinW, children)
      default:
        return children
    }
  }
  return ''
}

export interface SpeakData {
  name: string
  role: string
  style: string
  speed: string
  pitch: string
  ssml: string
}

/**
 * 默认 speaker节点
 * @returns
 */
function defaultSpeakNode(): Speak {
  return {
    type: 'ssml-speak',
    remark: '',
    version: '1.0',
    'xml:lang': 'zh-CN',
    xmlns: 'http://www.w3.org/2001/10/synthesis',
    'xmlns:mstts': '',
    'xmlns:emo': '',
    children: [],
  }
}

/**
 * 默认段落停顿
 */
function paragraphBreak(): Break {
  return {
    type: 'ssml-break',
    strength: 'medium',
    children: [{ text: '' }],
    remark: 'paragraphBreak',
  }
}

/**
 * 合并段落,并添加停顿
 * @param editor IDomEditor对象
 * @returns 合并后的节点
 */
function mergeParagraphNodes(editor: IDomEditor): SlateNode[] {
  const arrayList = editor.children
    .filter((v) => DomEditor.checkNodeType(v, 'paragraph'))
    .filter((v) => !SlateEditor.isEmpty(editor, v as SlateElement))
    .map((v, i, ls) => {
      const elem = v as SlateElement
      const list = elem.children as SlateNode[]
      if (i < ls.length - 1) return list.concat([paragraphBreak()])
      return list
    })
  return ([] as SlateNode[]).concat(...arrayList)
}

/**
 * createDefaultSpeakDataHandler
 * @param pushParent
 * @returns
 */
function createDefaultSpeakDataHandler(pushParent: (item: SpeakData) => void) {
  const { rootVoice, rootExpressAs, rootProsody } = useSSMLStore()
  const speakData: SpeakData = {
    name: rootVoice.name,
    role: rootExpressAs.role || '',
    style: rootExpressAs.style,
    speed: rootProsody.rate || '1',
    pitch: rootProsody.pitch || '0',
    ssml: '',
  }
  const speakNode = defaultSpeakNode()

  pushParent(speakData)

  function pushNode(node: SlateNode) {
    speakNode.children.push(node)
  }

  function serialize() {
    speakData.ssml = serializeNode(speakNode)
  }

  return { pushNode, serialize }
}

/**
 * customManagmentToSpeakData
 */
function customManagmentToSpeakData(customNode: CustomManagement): SpeakData {
  const speakNode = defaultSpeakNode()
  speakNode.children = customNode.children
  const speakSSML = serializeNode(speakNode)
  return {
    name: customNode.name,
    role: customNode.role,
    style: customNode.style,
    speed: customNode.rate,
    pitch: customNode.pitch,
    ssml: speakSSML,
  }
}

/**
 * 转换为 Speaker
 * @param editor
 * @returns
 */
function converToSpeakDataList(editor: IDomEditor): SpeakData[] {
  const nodes = mergeParagraphNodes(editor)
  const list: SpeakData[] = []
  let handler: ReturnType<typeof createDefaultSpeakDataHandler> | undefined
  for (let i = 0; i < nodes.length; i++) {
    const node = nodes[i]
    // 跳过空节点
    if (SlateText.isText(node) && !node.text.trim()) continue
    // 多人语音节点
    if (DomEditor.checkNodeType(node, 'custom-management')) {
      handler?.serialize()
      handler = undefined
      list.push(customManagmentToSpeakData(node as CustomManagement))
      continue
    }
    handler ??= createDefaultSpeakDataHandler((item) => list.push(item))
    handler.pushNode(node)
  }
  handler?.serialize()
  return list
}

/**
 * 序列化 Speak 数据列表
 * @returns
 */
export function serializeToSpeakDataList() {
  const { editor } = useEditorStore()
  if (!editor) throw Error('没有找到 speaker-editor 对象')
  const speaks = converToSpeakDataList(editor)
  return speaks
}

/**
 *
 * @returns 序列化为 SSML
 */
export default function serializeToSSML() {
  const list = serializeToSpeakDataList()
  function speakDataToXML(data: SpeakData) {
    // return `<with name="${data.name}" role="${data.role}" style="${data.style}" speed="${data.speed}" pitch="${data.pitch}">${data.ssml}</with>`
    return `${data.ssml}`
  }
  const ssml = list.map((v) => speakDataToXML(v)).join('')
  // return `<ssml>${ssml}</ssml>`
  return `${ssml}`
}
