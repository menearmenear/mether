#!/usr/bin/env python3
"""Mether mod sound generator - generates OGG sound files using sox."""
import os
import math
import random
import subprocess
import struct
import wave
import tempfile

SOUND_DIR = "src/main/resources/assets/mether/sounds"
os.makedirs(SOUND_DIR, exist_ok=True)

SAMPLE_RATE = 44100

def write_wav(path, samples, sr=SAMPLE_RATE):
    """Write PCM samples (list of floats -1..1) to a WAV file."""
    with wave.open(path, 'w') as w:
        w.setnchannels(1)
        w.setsampwidth(2)
        w.setframerate(sr)
        pcm = bytes()
        for s in samples:
            v = max(-1.0, min(1.0, s))
            pcm += struct.pack('<h', int(v * 32767))
        w.writeframes(pcm)

def to_ogg(wav_path, ogg_path):
    """Convert WAV to OGG using sox."""
    subprocess.run(
        ["sox", wav_path, ogg_path],
        check=True, capture_output=True
    )

def generate_sound(name, samples, sr=SAMPLE_RATE):
    """Generate a sound from samples and save as .ogg in the right dir."""
    ogg_path = os.path.join(SOUND_DIR, name + ".ogg")
    os.makedirs(os.path.dirname(ogg_path), exist_ok=True)
    with tempfile.NamedTemporaryFile(suffix=".wav", delete=False) as tmp:
        tmp_wav = tmp.name
    write_wav(tmp_wav, samples, sr)
    try:
        to_ogg(tmp_wav, ogg_path)
    finally:
        os.unlink(tmp_wav)
    print(f"  {name}.ogg generated")
    return ogg_path

def silence(duration):
    n = int(duration * SAMPLE_RATE)
    return [0.0] * n

def tone(freq, duration, volume=0.5, sr=SAMPLE_RATE):
    n = int(duration * sr)
    return [volume * math.sin(2 * math.pi * freq * i / sr) for i in range(n)]

def tone_decay(freq, duration, volume=0.5, decay=0.35, sr=SAMPLE_RATE):
    """Tone with exponential decay (chime-like)."""
    n = int(duration * sr)
    out = []
    for i in range(n):
        t = i / sr
        env = math.exp(-t * decay)
        out.append(volume * env * math.sin(2 * math.pi * freq * t))
    return out

def noise(duration, volume=0.5, sr=SAMPLE_RATE):
    n = int(duration * sr)
    rnd = random.Random()
    return [volume * (rnd.random() * 2 - 1) for i in range(n)]

def sweep(f_start, f_end, duration, volume=0.5, sr=SAMPLE_RATE):
    """Frequency sweep for whoosh sounds."""
    n = int(duration * sr)
    out = []
    for i in range(n):
        t = i / sr
        f = f_start + (f_end - f_start) * t / duration
        phase = (2 * math.pi * (f_start * t + 0.5 * (f_end - f_start) * t * t / duration))
        out.append(volume * math.sin(phase))
    return out

def chime(base_freq, duration=1.5, volume=0.4):
    """Crystal chime: base + harmonic."""
    s1 = tone_decay(base_freq, duration, volume, 2.5)
    s2 = tone_decay(base_freq * 1.5, duration, volume * 0.5, 3.0)
    s3 = tone_decay(base_freq * 2.0, duration, volume * 0.3, 3.5)
    return [s1[i] + s2[i] + s3[i] for i in range(min(len(s1), len(s2), len(s3)))]

def rumble(freq=40, duration=1.0, volume=0.6):
    """Deep monster rumble."""
    n = int(duration * SAMPLE_RATE)
    rnd = random.Random(99)
    out = []
    for i in range(n):
        t = i / SAMPLE_RATE
        env = math.exp(-t * 1.5)
        out.append(volume * env * math.sin(2 * math.pi * freq * t) * (0.8 + 0.2 * rnd.random()))
    return out

def mix(*tracks, volume=1.0):
    n = max(len(t) for t in tracks)
    out = [0.0] * n
    for t in tracks:
        for i in range(len(t)):
            out[i] += t[i]
    return [v * volume for v in out]

def repeat_melody(duration, base_freq, notes):
    """Generate a simple melody by repeating a note sequence."""
    total = int(duration * SAMPLE_RATE)
    out = [0.0] * total
    idx = 0
    while idx < total:
        f = base_freq * notes[idx % len(notes)]
        dur = 0.35
        n = int(dur * SAMPLE_RATE)
        for j in range(min(n, total - idx)):
            t = j / SAMPLE_RATE
            env = math.exp(-t * 4)
            out[idx + j] = 0.3 * env * math.sin(2 * math.pi * f * t)
        idx += n
    return out

print("=== Generating crystal block sounds ===")
for crystal in ["crystal"]:
    print("  block.crystal.break")
    s = mix(chime(880, 0.4, 0.5), noise(0.05, 0.1))
    generate_sound("block/crystal/break", s)
    print("  block.crystal.place")
    s = mix(chime(660, 0.3, 0.4), noise(0.03, 0.1))
    generate_sound("block/crystal/place", s)
    print("  block.crystal.step")
    s = mix(chime(440, 0.1, 0.2), noise(0.02, 0.15))
    generate_sound("block/crystal/step", s)
    print("  block.crystal.hit")
    generate_sound("block/crystal/hit", chime(990, 0.15, 0.3))
    print("  block.crystal.fall")
    generate_sound("block/crystal/fall", chime(550, 0.2, 0.15))

print("=== Generating portal sounds ===")
print("  portal.activate")
s = mix(sweep(200, 1200, 1.2, 0.5), chime(440, 1.5, 0.2))
generate_sound("portal/activate", s)
print("  portal.ambient")
s = mix(sweep(300, 900, 2.0, 0.2), chime(220, 2.0, 0.1))
generate_sound("portal/ambient", s)
print("  portal.travel")
s = mix(sweep(800, 200, 1.5, 0.4), chime(330, 1.5, 0.15))
generate_sound("portal/travel", s)

print("=== Generating item sounds ===")
print("  item.crystal.equip")
generate_sound("item/crystal/equip", mix(chime(1320, 0.5, 0.3), chime(1760, 0.4, 0.2)))
print("  item.crystal.pickup")
generate_sound("item/crystal/pickup", chime(880, 0.3, 0.25))

print("=== Generating mob sounds ===")
# Crystal Golem - heavy stone
print("  mob.crystal_golem")
generate_sound("mob/crystal_golem/ambient", rumble(50, 1.0, 0.4))
generate_sound("mob/crystal_golem/hurt", mix(rumble(60, 0.5, 0.5), noise(0.1, 0.2)))
generate_sound("mob/crystal_golem/death", mix(rumble(40, 1.5, 0.6), chime(220, 1.0, 0.2)))

# Crystal Spider - hissy
print("  mob.crystal_spider")
s = mix(noise(0.5, 0.2), sweep(1200, 800, 0.5, 0.2))
generate_sound("mob/crystal_spider/ambient", s)
generate_sound("mob/crystal_spider/hurt", mix(noise(0.3, 0.3), sweep(1500, 600, 0.3, 0.3)))
generate_sound("mob/crystal_spider/death", mix(noise(0.5, 0.2), sweep(1000, 300, 0.5, 0.3)))

# Shadow Wraith - eerie
print("  mob.shadow_wraith")
s = mix(sweep(400, 150, 1.0, 0.3), sweep(150, 400, 1.0, 0.15))
generate_sound("mob/shadow_wraith/ambient", s)
generate_sound("mob/shadow_wraith/hurt", sweep(600, 200, 0.4, 0.4))
generate_sound("mob/shadow_wraith/death", sweep(800, 80, 1.0, 0.35))

# Crystal Mage - magical chime
print("  mob.crystal_mage")
generate_sound("mob/crystal_mage/ambient", chime(660, 1.0, 0.3))
generate_sound("mob/crystal_mage/hurt", chime(880, 0.3, 0.4))
generate_sound("mob/crystal_mage/death", mix(chime(440, 1.0, 0.3), chime(330, 1.2, 0.2)))
print("  mob.crystal_mage.cast")
generate_sound("mob/crystal_mage/cast", mix(sweep(200, 1000, 0.5, 0.5), chime(1100, 0.5, 0.3)))

# Unstable Elemental - crackling
print("  mob.unstable_elemental")
s = mix(noise(0.5, 0.15), tone_decay(880, 0.3, 0.2, 3))
generate_sound("mob/unstable_elemental/ambient", s)
generate_sound("mob/unstable_elemental/hurt", mix(noise(0.3, 0.3), tone_decay(440, 0.3, 0.3, 2)))
generate_sound("mob/unstable_elemental/death", mix(noise(0.5, 0.3), sweep(1000, 100, 0.5, 0.4)))
print("  mob.unstable_elemental.explode")
generate_sound("mob/unstable_elemental/explode", mix(noise(0.8, 0.6), sweep(200, 50, 0.8, 0.5)))

print("=== Generating boss sounds ===")
# Luminarch - grand
print("  mob.luminarch")
s = mix(rumble(45, 2.0, 0.5), chime(110, 2.0, 0.2))
generate_sound("mob/luminarch/ambient", s)
generate_sound("mob/luminarch/hurt", mix(rumble(55, 0.6, 0.5), chime(220, 0.6, 0.3)))
generate_sound("mob/luminarch/death", mix(rumble(30, 3.0, 0.7), chime(110, 2.0, 0.4)))
print("  mob.luminarch.attack")
generate_sound("mob/luminarch/attack", mix(rumble(60, 0.8, 0.6), sweep(300, 900, 0.8, 0.4)))
print("  mob.luminarch.roar")
generate_sound("mob/luminarch/roar", mix(rumble(40, 1.5, 0.7), sweep(100, 300, 1.5, 0.4)))

print("=== Generating music (simple melodies) ===")
# Crystal Plains - peaceful pentatonic melody
print("  music.crystal_plains")
mel = [1, 1.25, 1.5, 2, 1.5, 1.25, 1, 0.75]
s = repeat_melody(4.0, 392, mel)
generate_sound("music/crystal_plains", s)

# Crystal Forest - mysterious
print("  music.crystal_forest")
mel = [1, 0.5, 0.75, 1.5, 1, 0.75, 0.5, 1.25]
s = repeat_melody(4.0, 293, mel)
generate_sound("music/crystal_forest", s)

# Boss music - intense low melody
print("  music.boss.luminarch")
mel = [1, 1.5, 1, 0.75, 1, 1.5, 2, 1.5]
s = mix(repeat_melody(4.0, 131, mel), rumble(50, 4.0, 0.2))
generate_sound("music/boss/luminarch", s)

print("\n=== ALL SOUNDS GENERATED SUCCESSFULLY ===")